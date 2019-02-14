package bg.bulsi.crc.service;

import bg.bulsi.crc.config.properties.SamlSpProperties;
import bg.bulsi.eauth.common.XMLNamespaceEnum;
import bg.bulsi.eauth.common.util.Base64Utility;
import bg.bulsi.eauth.common.util.PortalUtils;
import bg.bulsi.eauth.common.util.XMLUtils;
import bg.bulsi.eauth.exception.EAuthExceptionType;
import bg.bulsi.eauth.exception.EAuthProcessException;
import bg.bulsi.eauth.model.AuthData;
import bg.bulsi.eauth.model.UserData;
import bg.bulsi.eauth.saml.authnrequest.AuthRequestConstants;
import bg.bulsi.eauth.saml.authnrequest.dom.AuthRqParams;
import bg.bulsi.eauth.saml.authnrequest.dom.AuthnRequest;
import bg.bulsi.eauth.saml.authnrequest.dom.AuthnRequestIssuer;
import bg.bulsi.eauth.saml.authnrequest.dom.extensions.AuthnRequestExtensions;
import bg.bulsi.eauth.saml.response.AuthenticationUseCase;
import bg.bulsi.eauth.saml.response.AuthenticationUseCaseDefiner;
import bg.bulsi.eauth.saml.response.SAMLResponseStatus;
import bg.bulsi.eauth.saml.response.SamlResponseExtractor;
import bg.bulsi.eauth.system.util.ConfigConstants;
import com.sun.org.apache.xml.internal.security.transforms.Transforms;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.higgins.util.saml.X509KeySelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.security.auth.x500.X500Principal;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.keyinfo.X509IssuerSerial;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class eAVTService {

    private final SamlSpProperties properties;
    private final ResourceLoader resourceLoader;

    @Autowired
    public eAVTService(SamlSpProperties properties, ResourceLoader resourceLoader) {
        this.properties = properties;
        this.resourceLoader = resourceLoader;
    }

    public AuthRqParams generateSamlRequest() throws EAuthProcessException {
        try {
            AuthnRequestXMLBuilder authnRequestXMLBuilder = new AuthnRequestXMLBuilder(properties);

            String samlAuthnRequest = authnRequestXMLBuilder.buildXML(createEventManagementSystemServiceService());
            String samlRequest = Base64Utility.encode(samlAuthnRequest);
            String relayState = Base64Utility.encode(properties.getRelayState());

            return new AuthRqParams(samlRequest, relayState, properties.getSaml().getEavtUrl());

        } catch (Exception e) {
            String msg = "Error while generating Saml request";
            log.error(msg, e);
            throw new EAuthProcessException(msg, e, EAuthExceptionType.SYSTEM_EXCEPTION);
        }
    }

    public AuthData parseSamlResponse(String samlRsBase64, String relayStateBase64) {
        return new SamlParser().extractUserDetailsFromSamlRs(samlRsBase64, relayStateBase64);
    }

    private bg.bulsi.eauth.model.Service createEventManagementSystemServiceService() {

        return new bg.bulsi.eauth.model.Service(
                properties.getService().getServiceName(),
                properties.getService().getServiceOid(),
                properties.getService().getServiceUrl(),
                properties.getService().getServiceProviderName(),
                properties.getService().getServiceProviderOid()
        );
    }

    private void dumpSamlDocument(Node samlResponseDocument) {
        if (log.isDebugEnabled()) {
            try {
                XMLUtils.dumpDocument(samlResponseDocument);
            } catch (TransformerException e1) {
                log.error("Error dumping SAML Response Document! This is not fatal, the process will continue!");
            }
        }
    }

    class AuthnRequestXMLBuilder {

        private static final String SAML_ID_DATE_FORMAT = "yyyyMMddHHmmssSS";

        private final SamlSpProperties properties;

        AuthnRequestXMLBuilder(SamlSpProperties properties) {
            this.properties = properties;
        }

        private AuthnRequest buildAuthRequest(bg.bulsi.eauth.model.Service service) {

            /*
             * Extract values from the given service.
             */
            @SuppressWarnings("unused")
            String serviceName = service.getServiceName();
            String serviceOid = service.getServiceOid();
            String serviceUrl = service.getServiceUrl();

            String serviceProviderName = service.getServiceProviderName();
            String serviceProviderOid = service.getServiceProviderOid();

            /*
             * Build authnRequest.
             */
            AuthnRequest authRequest = buildEmptyAuthRequest();

            // TODO Use specific value for ID
            String id = generateSamlAuthnRequestID();
            authRequest.setId(id);
            authRequest.setDestination(properties.getSaml().getEavtUrl());
            authRequest.setProviderName(serviceProviderName);
            authRequest.setAssertionConsumerServiceURL(serviceUrl);

            /*
             * Build Issuer and add it to authRequest.
             */
            AuthnRequestIssuer issuer = new AuthnRequestIssuer();
            issuer.setsPProviderID(properties.getSaml().getPortalOid());
            issuer.setIssuerURL(properties.getSaml().getPortalUrl());

            authRequest.setIssuer(issuer);

            /*
             * Build samlp:Extensions and add them to authRequest.
             */
            AuthnRequestExtensions extensions = new AuthnRequestExtensions();
            extensions.setOids(serviceOid, serviceProviderOid);
            authRequest.setExtensions(extensions);

            return authRequest;
        }


        private AuthnRequest buildEmptyAuthRequest() {
            AuthnRequest authRequest = new AuthnRequest();

            authRequest.setVersion(AuthRequestConstants.VERSION);
            authRequest.setProtocolBinding(AuthRequestConstants.PROTOCOL_BINDING);
            authRequest.setForceAuthn(AuthRequestConstants.FORCE_AUTH);
            authRequest.setIsPassive(AuthRequestConstants.IS_PASIVE);

            String dateString = PortalUtils.getFormatedTimestamp();
            authRequest.setIssueInstant(dateString);

            return authRequest;
        }


        String generateSamlAuthnRequestID() {

            String samlIdPrefix = properties.getSaml().getIdPrefix();

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(SAML_ID_DATE_FORMAT);
            Date now = new Date();
            String dateString = simpleDateFormat.format(now);

            return samlIdPrefix + "_" + dateString;
        }

        /**
         * Create SAML AuthnRequest string representation from given Service.
         *
         * @param service Service information
         * @return String representation of constructed SAML AuthnRequest XML
         * @throws EAuthProcessException exception
         */
        String buildXML(bg.bulsi.eauth.model.Service service) throws EAuthProcessException, KeyStoreException {
            AuthnRequest authnRequest = buildAuthRequest(service);
            Document authnRequestXMLDocument = buildXMLDocument(authnRequest);


            /*
             * For test purpose: Add some XML content in SPContext - books catalog.
             */
//		String sPContextXMLFilePath = PortalConfiguration.getTempWorkingDirectory() + File.separator + "books_catalog.xml";
//		Document bookCatalog = XMLUtils.createXMLDocumentFromFile(sPContextXMLFilePath);
//		authnRequestXMLDocument = addSpContextDataToAuthnRequest(authnRequestXMLDocument, bookCatalog);

            /*
             * Sign the document.
             */
            Document signedAuthnRequestXMLDocument = new DigitalSigner(properties).signXMLDocument(authnRequestXMLDocument);

            /*
             *  Save constructed XML Document in a file.
             *  This is for test purpose.
             *  Remove it after be sure that everything works.
             */
//		String signedSamlAuthnRequestXMLFilePath = PortalConfiguration.getTempWorkingDirectory() + "\\" + "signedxml_portal.xml";
//		try {
//			XMLUtils.saveDocumentInFile(signedAuthnRequestXMLDocument, signedSamlAuthnRequestXMLFilePath);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}


            return XMLUtils.createStringByXMLDocument(signedAuthnRequestXMLDocument);
        }


        private Document buildXMLDocument(AuthnRequest authnRequest) throws EAuthProcessException {
            /*
             * Creating XML Document
             */
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setNamespaceAware(true);

            DocumentBuilder dBuilder;
            try {
                dBuilder = dbFactory.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                throw new EAuthProcessException("Can not build XML by AuthnRequest object - dbFactory.newDocumentBuilder()!", e, EAuthExceptionType.SYSTEM_EXCEPTION);
            }

            Document document = dBuilder.newDocument();

            try {
                /*
                 * Marshaling object AuthnRequest into XML Document
                 */
                JAXBContext jaxbContext = JAXBContext.newInstance(AuthnRequest.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

//			StringWriter stringWriter = new StringWriter();
//			jaxbMarshaller.marshal(authnRequest, stringWriter);

                jaxbMarshaller.marshal(authnRequest, document);

            } catch (JAXBException e) {
                throw new EAuthProcessException("Problem creating SAML AuthnRequest XML Document!", e, EAuthExceptionType.SYSTEM_EXCEPTION);
            }

            if (document != null) {
                document.normalize();
            }

            return document;
        }


        @SuppressWarnings("unused")
        private Document addSpContextDataToAuthnRequest(Document authnRequestXMLDocument, Document bookCatalog) throws EAuthProcessException {
            bookCatalog.normalize();
            Node nodeBooks = bookCatalog.getFirstChild();

            authnRequestXMLDocument.normalize();
            NodeList spContextNodes = authnRequestXMLDocument.getElementsByTagNameNS(
                    XMLNamespaceEnum.EGOV_BG_A.getUri(),
                    ConfigConstants.XML_TAGNAME_SPCONTEXT
            );

            if (spContextNodes.getLength() == 0) {
                throw new EAuthProcessException("The SAMl AuthnRequest has no SPContext tag!", EAuthExceptionType.USER_EXCEPTION);
            }

            Node nodeSpContext = spContextNodes.item(0);
            Node importedSpContextNode = authnRequestXMLDocument.importNode(nodeBooks, true);
            nodeSpContext.appendChild(importedSpContextNode);

            return authnRequestXMLDocument;
        }

    }

    class DigitalSigner {

        private static final String SIGNATURE_NS_LOCAL_NAME = "Signature";
        private static final String XML_DOM = "DOM";

        private final SamlSpProperties properties;
        private final KeyStore keyStore;

        DigitalSigner(SamlSpProperties properties) throws KeyStoreException {
            this.properties = properties;
            this.keyStore = KeyStore.getInstance(properties.getDigitalSignature().getKeystoreType());
        }

        /**
         * @param objectForSigning object for signing
         * @return SignedObject
         */
        public SignedObject getSignedObject(String objectForSigning) throws EAuthProcessException {

            String privateKeyAlias = properties.getDigitalSignature().getPrivateAlias();
            String privateKeyPass = properties.getDigitalSignature().getPrivateKeyPass();

            loadKeystoreData(properties.getDigitalSignature().getKeystoreFileName());

            try {
                PrivateKey privateKey = (PrivateKey) keyStore.getKey(privateKeyAlias, privateKeyPass.toCharArray());
                Signature signature = Signature.getInstance(privateKey.getAlgorithm());
                return new SignedObject(objectForSigning, privateKey, signature);

            } catch (NoSuchAlgorithmException | IOException | SignatureException |
                    InvalidKeyException | UnrecoverableKeyException | KeyStoreException e) {
                throw new EAuthProcessException("Can not generate signed object!", e, EAuthExceptionType.SYSTEM_EXCEPTION);
            }
        }

        Document signXMLDocument(Document document) throws EAuthProcessException {

            document.normalize();
            Node root = document.getDocumentElement();

            loadKeystoreData(properties.getDigitalSignature().getKeystoreFileName());

            PrivateKey privateKey;
            try {
                privateKey = (PrivateKey) keyStore.getKey(
                        properties.getDigitalSignature().getPrivateAlias(),
                        properties.getDigitalSignature().getPrivateKeyPass().toCharArray()
                );
                X509Certificate certificate = (X509Certificate) keyStore.getCertificate(
                        properties.getDigitalSignature().getPrivateAlias()
                );
                certificate.getPublicKey();

                dumpSamlDocument(root);

            } catch (UnrecoverableKeyException | KeyStoreException e) {
                throw new EAuthProcessException("Can not define PrivateKey or PublicKey from keystore!", e,
                        EAuthExceptionType.SYSTEM_EXCEPTION);

            } catch (NoSuchAlgorithmException e) {
                throw new EAuthProcessException(
                        "Can not define PrivateKey or PublicKey from keystore, the algorithm is wrong!", e,
                        EAuthExceptionType.SYSTEM_EXCEPTION);
            }

            XMLSignatureFactory sigFactory = XMLSignatureFactory.getInstance("DOM");

            Transform transformEnveloped;
            Transform transformC14N;

            try {
                transformEnveloped = sigFactory.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null);
                transformC14N = sigFactory.newTransform(
                        Transforms.TRANSFORM_C14N_OMIT_COMMENTS, (TransformParameterSpec) null);
            } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
                throw new EAuthProcessException("Can not create XML transformers for Envelope and C14N!", e,
                        EAuthExceptionType.SYSTEM_EXCEPTION);
            }

            try {
                List<Transform> transformList = new ArrayList<>();
                transformList.add(transformEnveloped);
                transformList.add(transformC14N);

                Reference reference = sigFactory.newReference("", sigFactory.newDigestMethod(DigestMethod.SHA1, null), transformList,
                        null, null);
                SignedInfo signedInfo = sigFactory.newSignedInfo(
                        sigFactory.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE,
                                (C14NMethodParameterSpec) null),
                        sigFactory.newSignatureMethod(SignatureMethod.DSA_SHA1, null),
                        Collections.singletonList(reference));

                List<Object> x509Content = new ArrayList<>();

                X509Certificate certificate = (X509Certificate) keyStore.getCertificate(properties.getDigitalSignature().getPrivateAlias());

                KeyInfoFactory kif = sigFactory.getKeyInfoFactory();
                X509IssuerSerial x509IssuerSerial = kif.newX509IssuerSerial(
                        certificate.getIssuerX500Principal().getName(X500Principal.RFC1779), certificate.getSerialNumber());

                String subjectName = certificate.getSubjectX500Principal().getName();

                x509Content.add(x509IssuerSerial);
                x509Content.add(subjectName);
                x509Content.add(certificate);
                X509Data x509Data = kif.newX509Data(x509Content);
                KeyInfo keyInfo = kif.newKeyInfo(Collections.singletonList(x509Data));

                Element documentElement = document.getDocumentElement();
                documentElement.normalize();

                NodeList nodeListExtensions = document.getElementsByTagNameNS("urn:oasis:names:tc:SAML:2.0:protocol",
                        "Extensions");
                if (nodeListExtensions.getLength() == 0) {
                    throw new EAuthProcessException("The created SAML AuthnRequest is not in right format!",
                            EAuthExceptionType.SYSTEM_EXCEPTION);
                }
                Node nodeExtensions = nodeListExtensions.item(0);

                DOMSignContext domSignContext = new DOMSignContext(privateKey, documentElement, nodeExtensions);
                domSignContext.setDefaultNamespacePrefix("ds");

                XMLSignature signature = sigFactory.newXMLSignature(signedInfo, keyInfo);

                signature.sign(domSignContext);

                document.normalize();

                dumpSamlDocument(root);

                return document;

            } catch (KeyStoreException e) {
                throw new EAuthProcessException("Can not create X509Certificate certificate by given allias in keyStore!",
                        e, EAuthExceptionType.SYSTEM_EXCEPTION);
            } catch (MarshalException | XMLSignatureException e) {
                throw new EAuthProcessException("The created SAML AuthnRequest is in wrong format and can not be signed!!",
                        e, EAuthExceptionType.SYSTEM_EXCEPTION);
            } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
                throw new EAuthProcessException("Can not create SignedInfo when trying to sign Auth Request XML!", e,
                        EAuthExceptionType.SYSTEM_EXCEPTION);
            }
        }

        private void loadKeystoreData(String keystoreFilePath) throws EAuthProcessException {

            Resource r = resourceLoader.getResource(keystoreFilePath);

            try (InputStream fileInputStream = r.getInputStream()) {
                keyStore.load(fileInputStream, properties.getDigitalSignature().getKeystorePass().toCharArray());
            } catch (IOException e) {
                throw new EAuthProcessException("An I/O or format problem with the keystore data", e, EAuthExceptionType.SYSTEM_EXCEPTION);
            } catch (CertificateException e) {
                throw new EAuthProcessException("Any of the certificates in the keystore could not be loaded", e, EAuthExceptionType.SYSTEM_EXCEPTION);
            } catch (NoSuchAlgorithmException e) {
                throw new EAuthProcessException("The algorithm used to check the integrity of the keystore cannot be found", e, EAuthExceptionType.SYSTEM_EXCEPTION);
            }
        }

        private KeyStore getKeyStore(String keystoreType) throws EAuthProcessException {
            KeyStore keyStore;
            try {
                keyStore = KeyStore.getInstance(keystoreType);
            } catch (KeyStoreException e) {
                throw new EAuthProcessException("Can not initialize KeyStore by Type!", e, EAuthExceptionType.SYSTEM_EXCEPTION);
            }
            return keyStore;
        }

        public boolean isSignedXMLValid(Document document) throws EAuthProcessException {

            Node signatureNode = method2(document);

            DOMValidateContext validateContext = new DOMValidateContext(new X509KeySelector(), signatureNode);

            return method1(validateContext);
        }

        private Node method2(Document document) throws EAuthProcessException {
            if (document == null) {
                String errorMessage = "Not XML parsable SAML Response!";
                throw new EAuthProcessException(errorMessage, EAuthExceptionType.SYSTEM_EXCEPTION);
            }

            document.normalize();

            NodeList nodeList = document.getElementsByTagNameNS(XMLSignature.XMLNS, SIGNATURE_NS_LOCAL_NAME);
            if (nodeList.getLength() == 0) {
                String errorMessage = "Cannot find Signature element in SAML Response XML!";
                throw new EAuthProcessException(errorMessage, EAuthExceptionType.SYSTEM_EXCEPTION);
            }
            return nodeList.item(0);
        }

        private boolean method1(DOMValidateContext validateContext) throws EAuthProcessException {
            XMLSignatureFactory xmlSignatureFactory = XMLSignatureFactory.getInstance(XML_DOM);
            XMLSignature xmlSignature;

            try {
                xmlSignature = xmlSignatureFactory.unmarshalXMLSignature(validateContext);
            } catch (MarshalException e) {
                String errorMessage = "There is a problem reading digital signature of authentication response!";
                throw new EAuthProcessException(errorMessage, e, EAuthExceptionType.SYSTEM_EXCEPTION);
            }

            boolean isSignedXMLValid;
            try {
                isSignedXMLValid = xmlSignature.validate(validateContext);
            } catch (XMLSignatureException e) {
                String errorMessage = "There is a problem reading digital signature of authentication response!";
                throw new EAuthProcessException(errorMessage, e, EAuthExceptionType.SYSTEM_EXCEPTION);
            }

            return isSignedXMLValid;
        }

        boolean isSignedXMLValidByCertificate(Document document) throws EAuthProcessException {

            Node signatureNode = method2(document);

            String certificatePathEAuthenticator = properties.getDigitalSignature().getEavtCertificateName();

            X509Certificate certificateEAuthenticator = createX509CertificateByFilePath(certificatePathEAuthenticator);

            if (certificateEAuthenticator == null) {
                throw new EAuthProcessException(
                        "There is a problem reading data from Authenticator TrustStore for outer systems",
                        EAuthExceptionType.SYSTEM_EXCEPTION);
            }

            PublicKey certificatePublicKey = certificateEAuthenticator.getPublicKey();
            DOMValidateContext validateContext = new DOMValidateContext(X509KeySelector.singletonKeySelector(certificatePublicKey), signatureNode);

            return method1(validateContext);
        }

        private X509Certificate createX509CertificateByFilePath(String certificatePath) throws EAuthProcessException {

            if (certificatePath == null) {
                throw new EAuthProcessException("X509 Certificate path must not be NULL!", EAuthExceptionType.USER_EXCEPTION);
            }

            Resource r = resourceLoader.getResource(certificatePath);

            try (InputStream certificateIS = r.getInputStream()) {
                CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
                return (X509Certificate) certificateFactory.generateCertificate(certificateIS);
            } catch (IOException e) {
                throw new EAuthProcessException(
                        "Error creating X509Certificate, file " + certificatePath + " doesn't exist!", e,
                        EAuthExceptionType.SYSTEM_EXCEPTION);
            } catch (CertificateException e) {
                throw new EAuthProcessException("Error creating X509Certificate by file " + certificatePath, e,
                        EAuthExceptionType.SYSTEM_EXCEPTION);
            }
        }
    }

    class SamlParser {

        AuthData extractUserDetailsFromSamlRs(String samlRsBase64, String relayStateBase64) {

            log.debug("RELAYSTATE: " + relayStateBase64 + " SAMLRS: " + samlRsBase64);

            String samlResponse = null;
            String relayState = null;

            if (StringUtils.isNotBlank(relayStateBase64)) {
                relayState = Base64Utility.decode(relayStateBase64);
            }

            log.debug("relayState: " + relayState);

            if (StringUtils.isNotBlank(samlRsBase64)) {
                samlResponse = Base64Utility.decode(samlRsBase64);
            }
            log.debug("samlResponse: " + samlResponse);

            return processSAMLResponse(samlResponse);
        }

        private AuthData processSAMLResponse(String samlRs) {

            AuthData authData = new AuthData();

            /* Check Saml rs */
            if (StringUtils.isBlank(samlRs)) {
                log.info("SP called witout SAMLResponse request parameter");
                authData.setStatus(AuthenticationUseCase.INVALID_SAML_RS);

                return authData;
            }

            Document samlResponseDocument;
            /* String to xml */
            try {
                samlResponseDocument = XMLUtils.createXMLDocumentFromString(samlRs);
            } catch (EAuthProcessException e) {
                log.error("Can not create XML Document from passed request parameter SAMLResponse: " + e.getMessage(), e);
                authData.setStatus(AuthenticationUseCase.AUTHENTICATED_UNSUCCESSFULLY);

                return authData;
            }

            /* Xml dump */

            dumpSamlDocument(samlResponseDocument);

            /* Check SAML is valid */
            try {
                boolean isSignedXMLValid = new DigitalSigner(properties).isSignedXMLValidByCertificate(samlResponseDocument);

                if (!isSignedXMLValid) {
                    log.error("SAML Response integrity is not valid!");

                    authData.setStatus(AuthenticationUseCase.AUTHENTICATED_UNSUCCESSFULLY);
                    return authData;
                }
                log.info("Event Management System: SAML Response integrity is valid!");
            } catch (EAuthProcessException | KeyStoreException e) {
                log.error("Exception raised when checking if signed SAML Response is valid!", e);
                authData.setStatus(AuthenticationUseCase.AUTHENTICATED_UNSUCCESSFULLY);
                return authData;
            }

            /* Check SAML Response Status and define Authentication Use Case */
            try {
                SAMLResponseStatus samlResponseStatus = SamlResponseExtractor.getStatus(samlResponseDocument);
                authData.setStatus(AuthenticationUseCaseDefiner.defineAuthenticationUseCase(samlResponseStatus));

                log.info("SAML Response StatusCode! " + authData.getStatus().getDescriptionENG());
                if (authData.getStatus() != AuthenticationUseCase.AUTHENTICATED_SUCCESSFULLY) {
                    return authData;
                }
            } catch (EAuthProcessException e) {
                log.error("Error defining Authentication Use Case by SAML Response StatusCode!" + e.getMessage(), e);
                authData.setStatus(AuthenticationUseCase.AUTHENTICATED_UNSUCCESSFULLY);
                return authData;
            }

            /* Extract SAML Token */
            //Document samlResponseTokenDocument;
            try {
                Document samlResponseTokenDocument = SamlResponseExtractor.getToken(samlResponseDocument);
                if (samlResponseTokenDocument == null) {
                    log.error("There is no Token in SAML Response!");
                    authData.setStatus(AuthenticationUseCase.AUTHENTICATED_UNSUCCESSFULLY);
                    return authData;
                }

                /* Validate SAML Response token by calling STS validate. */
                // TODO fix this (!!!!)
                if (Boolean.parseBoolean(properties.getSaml().getMakeStsTokenValidation())) {
                    try {
                        boolean isSamlResponseTokenValid = isSamlResponseTokenValid(samlResponseTokenDocument);
                        if (!isSamlResponseTokenValid) {
                            log.error("SAML Response Token integrity is not valid! Checking has been done by STS validate.");
                            authData.setStatus(AuthenticationUseCase.AUTHENTICATED_UNSUCCESSFULLY);
                            return authData;
                        }
                        log.info("SAML Response Token integrity is valid! Checking has been done by STS validate.");
                    } catch (EAuthProcessException e) {
                        log.error("Error validating SAML Response Token by calling STS validate!", e);
                        authData.setStatus(AuthenticationUseCase.AUTHENTICATED_UNSUCCESSFULLY);
                        return authData;
                    }
                }

                UserData userData = createUserBySAMLResponse(samlResponseDocument);
                authData.setUserData(userData);

                return authData;

            } catch (EAuthProcessException e) {
                log.error("Error extracting token from SAML Response! ", e);

                authData.setStatus(AuthenticationUseCase.AUTHENTICATED_UNSUCCESSFULLY);
                return authData;
            }

        }

        private UserData createUserBySAMLResponse(Document samlResponseDocument) throws EAuthProcessException {

            try {
                String egn = SamlResponseExtractor.getSubjectEgn(samlResponseDocument);
                String name = SamlResponseExtractor.getName(samlResponseDocument);
                String email = SamlResponseExtractor.getEmail(samlResponseDocument);

                UserData bgEgovLoggedUser = new UserData();
                bgEgovLoggedUser.setEgn(egn);
                bgEgovLoggedUser.setName(name);
                bgEgovLoggedUser.setEmail(email);

                return bgEgovLoggedUser;

            } catch (EAuthProcessException e) {
                throw new EAuthProcessException("Can not extract data for user from SAML Response!", e,
                        EAuthExceptionType.SYSTEM_EXCEPTION);
            }
        }

        private boolean isSamlResponseTokenValid(Document samlResponseToken) throws EAuthProcessException {

            try {
                String token = XMLUtils.createStringByXMLDocument(samlResponseToken);
                if (StringUtils.isBlank(token)) {
                    throw new EAuthProcessException("SAML Response Token value is null or empty!",
                            EAuthExceptionType.SYSTEM_EXCEPTION);
                }

                // FIXME
                // STSFacade stsFacade = new STSFacade();
                boolean isTokenValid = false;
                // isTokenValid = stsFacade.isTokenValid(token);

                return isTokenValid;

            } catch (EAuthProcessException e) {
                throw new EAuthProcessException("Can not create string by SAML Response Token!", e,
                        EAuthExceptionType.SYSTEM_EXCEPTION);
            }
        }
    }
}
