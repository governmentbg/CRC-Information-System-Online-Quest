package bg.bulsi.crc.service;

public class SamlResponse {

    public static String samlRsBase64 = "PHNhbWxwOlJlc3BvbnNlIHhtbG5zOnNhbWxwPSJ1cm46b2FzaXM6bmFtZXM6dGM6U0FNTDoyLjA6\n" +
            "cHJvdG9jb2wiIHhtbG5zOmRzPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwLzA5L3htbGRzaWcjIiB4\n" +
            "bWxuczplZ292YmdhPSJ1cm46Ymc6ZWdvdjplYXV0aDoxLjA6c2FtbDpleHQiIHhtbG5zOnNhbWw9\n" +
            "InVybjpvYXNpczpuYW1lczp0YzpTQU1MOjIuMDphc3NlcnRpb24iIERlc3RpbmF0aW9uPSJodHRw\n" +
            "Oi8vMTcyLjIzLjEwNy43MTo4MDgwL2F1ZGl0TG9nUmVwb3J0L2V2ZW50c1JlcG9ydC5zZWFtIiBJ\n" +
            "RD0iMjAxODExMTQxNTU2MTAyODZfQXVkaXRMb2dSZXBvcnRQREFBXzIwMTgxMTE0MTU1NTU4NTc3\n" +
            "IiBJblJlc3BvbnNlVG89IkF1ZGl0TG9nUmVwb3J0UERBQV8yMDE4MTExNDE1NTU1ODU3NyIgSXNz\n" +
            "dWVJbnN0YW50PSIyMDE4LTExLTE0VDE1OjU2OjEwIiBWZXJzaW9uPSIyLjAiPjxkczpTaWduYXR1\n" +
            "cmU+PGRzOlNpZ25lZEluZm8+PGRzOkNhbm9uaWNhbGl6YXRpb25NZXRob2QgQWxnb3JpdGhtPSJo\n" +
            "dHRwOi8vd3d3LnczLm9yZy8yMDAxLzEwL3htbC1leGMtYzE0biMiLz48ZHM6U2lnbmF0dXJlTWV0\n" +
            "aG9kIEFsZ29yaXRobT0iaHR0cDovL3d3dy53My5vcmcvMjAwMC8wOS94bWxkc2lnI2RzYS1zaGEx\n" +
            "Ii8+PGRzOlJlZmVyZW5jZSBVUkk9IiI+PGRzOlRyYW5zZm9ybXM+PGRzOlRyYW5zZm9ybSBBbGdv\n" +
            "cml0aG09Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvMDkveG1sZHNpZyNlbnZlbG9wZWQtc2lnbmF0\n" +
            "dXJlIi8+PGRzOlRyYW5zZm9ybSBBbGdvcml0aG09Imh0dHA6Ly93d3cudzMub3JnLzIwMDEvMTAv\n" +
            "eG1sLWV4Yy1jMTRuIyIvPjwvZHM6VHJhbnNmb3Jtcz48ZHM6RGlnZXN0TWV0aG9kIEFsZ29yaXRo\n" +
            "bT0iaHR0cDovL3d3dy53My5vcmcvMjAwMC8wOS94bWxkc2lnI3NoYTEiLz48ZHM6RGlnZXN0VmFs\n" +
            "dWU+dWsrWUtjYURqaGZESDFIRXlSMkhqYUJGUzE0PTwvZHM6RGlnZXN0VmFsdWU+PC9kczpSZWZl\n" +
            "cmVuY2U+PC9kczpTaWduZWRJbmZvPjxkczpTaWduYXR1cmVWYWx1ZT5RK3k4ZG5WWTNmZUs0c1NG\n" +
            "dVBMYUxPbk83eGwyVWIwTjg2M29nK1A0cjhmQ1kwSFBHUTlFK0E9PTwvZHM6U2lnbmF0dXJlVmFs\n" +
            "dWU+PGRzOktleUluZm8+PGRzOlg1MDlEYXRhPjxkczpYNTA5SXNzdWVyU2VyaWFsPjxkczpYNTA5\n" +
            "SXNzdWVyTmFtZT5DTj1iZy5lZ292LmF1dGhlbnRpY2F0b3IsIE9VPUVBdXRoZW50aWNhdG9yLCBP\n" +
            "PU1USVRTLCBMPVNvZmlhLCBTVD1Tb2ZpYSwgQz1CRzwvZHM6WDUwOUlzc3Vlck5hbWU+PGRzOlg1\n" +
            "MDlTZXJpYWxOdW1iZXI+MTQzMzMxNzk5NDwvZHM6WDUwOVNlcmlhbE51bWJlcj48L2RzOlg1MDlJ\n" +
            "c3N1ZXJTZXJpYWw+PGRzOlg1MDlTdWJqZWN0TmFtZT5DTj1iZy5lZ292LmF1dGhlbnRpY2F0b3Is\n" +
            "T1U9RUF1dGhlbnRpY2F0b3IsTz1NVElUUyxMPVNvZmlhLFNUPVNvZmlhLEM9Qkc8L2RzOlg1MDlT\n" +
            "dWJqZWN0TmFtZT48ZHM6WDUwOUNlcnRpZmljYXRlPk1JSURKVENDQXVPZ0F3SUJBZ0lFVlc2eWFq\n" +
            "QUxCZ2NxaGtqT09BUURCUUF3ZGpFTE1Ba0dBMVVFQmhNQ1FrY3hEakFNQmdOVkJBZ1RCVk52Wm1s\n" +
            "aE1RNHdEQVlEVlFRSEV3VlRiMlpwWVRFT01Bd0dBMVVFQ2hNRlRWUkpWRk14RnpBVkJnTlZCQXNU\n" +
            "RGtWQmRYUm9aVzUwYVdOaGRHOXlNUjR3SEFZRFZRUURFeFZpWnk1bFoyOTJMbUYxZEdobGJuUnBZ\n" +
            "MkYwYjNJd0hoY05NVFV3TmpBek1EYzFNekUwV2hjTk1UZ3dOakF5TURjMU16RTBXakIyTVFzd0NR\n" +
            "WURWUVFHRXdKQ1J6RU9NQXdHQTFVRUNCTUZVMjltYVdFeERqQU1CZ05WQkFjVEJWTnZabWxoTVE0\n" +
            "d0RBWURWUVFLRXdWTlZFbFVVekVYTUJVR0ExVUVDeE1PUlVGMWRHaGxiblJwWTJGMGIzSXhIakFj\n" +
            "QmdOVkJBTVRGV0puTG1WbmIzWXVZWFYwYUdWdWRHbGpZWFJ2Y2pDQ0FiY3dnZ0VzQmdjcWhrak9P\n" +
            "QVFCTUlJQkh3S0JnUUQ5ZjFPQkhYVVNLVkxmU3B3dTdPVG45aEczVWp6dlJBRERIaitBdGxFbWFV\n" +
            "VmRRQ0pSKzFrOWpWajZ2OFgxdWpEMnk1dFZiTmVCTzRBZE5HL3labUMzYTVsUXBhU2ZuK2dFZXhB\n" +
            "aXdrKzdxZGYrdDhZYitEdFg1OGFvcGhVUEJQdUQ5dFBGSHNNQ05WUVRXaGFSTXZaMTg2NHJZZGNx\n" +
            "Ny9JaUF4bWQwVWdCeHdJVkFKZGdVSThWSXd2TXNwSzVncUxyaEF2d1dCejFBb0dCQVBmaG9JWFdt\n" +
            "ejNleTd5clhEYTRWN2w1bEsrNytqcnFndmxYVEFzOUI0Sm5VVmxYanJyVVdVL21jUWNRZ1lDMFNS\n" +
            "WnhJK2hNS0JZVHQ4OEpNb3pJcHVFOEZucUxWSHlOS09DanJoNHJzNloxa1c2amZ3djZJVFZpOGZ0\n" +
            "aWVnRWtPOHlrOGI2b1VaQ0pxSVBmNFZybG53YVNpMlplZ0h0VkpXUUJURHYrejBrcUE0R0VBQUtC\n" +
            "Z0dFVkV3MzF3dXZKODczOVltK0lUQ2RWS2trVnBONkQzSFpkZUh6SGFWRi9SRmkybWtEeW1pVFly\n" +
            "VlpRb0k1bDFhbU1RK1hoWmg5RWZ0UjJaNnEzWUF0YXpMSVo5Q0hGNnhZZnVxM3pKVFU1dUdvM1ZV\n" +
            "b1kwbURQWHZadlBNSkRrdzJBbm5vcFV2dGhaSEtHTFFXSlpZWVJIWkd1Yzc1dnZyc1JiTmhrNmhr\n" +
            "Yk1Bc0dCeXFHU000NEJBTUZBQU12QURBc0FoUmROTkNidHF5cXVob1VDTkV0REZJbHVqWmRQUUlV\n" +
            "SnBOUkhONnRGUE4yY3U3SFRxeCtUSHByUGhFPTwvZHM6WDUwOUNlcnRpZmljYXRlPjwvZHM6WDUw\n" +
            "OURhdGE+PC9kczpLZXlJbmZvPjwvZHM6U2lnbmF0dXJlPjxzYW1scDpTdGF0dXM+PHNhbWxwOlN0\n" +
            "YXR1c0NvZGUgVmFsdWU9InVybjpvYXNpczpuYW1lczp0YzpTQU1MOjIuMDpzdGF0dXM6U3VjY2Vz\n" +
            "cyIvPjxzYW1scDpTdGF0dXNNZXNzYWdlPtCj0YHQv9C10YjQtdC9INC+0YLQs9C+0LLQvtGAINC9\n" +
            "0LAg0LfQsNGP0LLQutCw0YLQsDwvc2FtbHA6U3RhdHVzTWVzc2FnZT48L3NhbWxwOlN0YXR1cz48\n" +
            "c2FtbDI6QXNzZXJ0aW9uIHhtbG5zOnNhbWwyPSJ1cm46b2FzaXM6bmFtZXM6dGM6U0FNTDoyLjA6\n" +
            "YXNzZXJ0aW9uIiB4bWxuczp4cz0iaHR0cDovL3d3dy53My5vcmcvMjAwMS9YTUxTY2hlbWEiIHht\n" +
            "bG5zOnhzaT0iaHR0cDovL3d3dy53My5vcmcvMjAwMS9YTUxTY2hlbWEtaW5zdGFuY2UiIElEPSJf\n" +
            "NkE5RThFOTBGNURGNzA2MTg5MTU0MjIwMzc0ODMwNzEiIElzc3VlSW5zdGFudD0iMjAxOC0xMS0x\n" +
            "NFQxMzo1NTo0OC4zMjZaIiBWZXJzaW9uPSIyLjAiIHhzaTp0eXBlPSJzYW1sMjpBc3NlcnRpb25U\n" +
            "eXBlIj48c2FtbDI6SXNzdWVyPmh0dHA6Ly9lYXV0aC5lZ292LmJnLzwvc2FtbDI6SXNzdWVyPjxk\n" +
            "czpTaWduYXR1cmU+PGRzOlNpZ25lZEluZm8+PGRzOkNhbm9uaWNhbGl6YXRpb25NZXRob2QgQWxn\n" +
            "b3JpdGhtPSJodHRwOi8vd3d3LnczLm9yZy8yMDAxLzEwL3htbC1leGMtYzE0biMiLz48ZHM6U2ln\n" +
            "bmF0dXJlTWV0aG9kIEFsZ29yaXRobT0iaHR0cDovL3d3dy53My5vcmcvMjAwMC8wOS94bWxkc2ln\n" +
            "I3JzYS1zaGExIi8+PGRzOlJlZmVyZW5jZSBVUkk9IiNfNkE5RThFOTBGNURGNzA2MTg5MTU0MjIw\n" +
            "Mzc0ODMwNzEiPjxkczpUcmFuc2Zvcm1zPjxkczpUcmFuc2Zvcm0gQWxnb3JpdGhtPSJodHRwOi8v\n" +
            "d3d3LnczLm9yZy8yMDAwLzA5L3htbGRzaWcjZW52ZWxvcGVkLXNpZ25hdHVyZSIvPjxkczpUcmFu\n" +
            "c2Zvcm0gQWxnb3JpdGhtPSJodHRwOi8vd3d3LnczLm9yZy8yMDAxLzEwL3htbC1leGMtYzE0biMi\n" +
            "PjxlYzpJbmNsdXNpdmVOYW1lc3BhY2VzIHhtbG5zOmVjPSJodHRwOi8vd3d3LnczLm9yZy8yMDAx\n" +
            "LzEwL3htbC1leGMtYzE0biMiIFByZWZpeExpc3Q9InhzIi8+PC9kczpUcmFuc2Zvcm0+PC9kczpU\n" +
            "cmFuc2Zvcm1zPjxkczpEaWdlc3RNZXRob2QgQWxnb3JpdGhtPSJodHRwOi8vd3d3LnczLm9yZy8y\n" +
            "MDAwLzA5L3htbGRzaWcjc2hhMSIvPjxkczpEaWdlc3RWYWx1ZT5FNFdyLzJDNFQxMnk4b1R0OThZ\n" +
            "RWpBdHFVb1E9PC9kczpEaWdlc3RWYWx1ZT48L2RzOlJlZmVyZW5jZT48L2RzOlNpZ25lZEluZm8+\n" +
            "PGRzOlNpZ25hdHVyZVZhbHVlPmV0emdjYy9qcmsrOUxjbHRUQmxBemc2cUF3NjlnOUV5QVdQN2VN\n" +
            "U3ZXcGtwbFJuanlmSEtpd3lnQURtQjRIT092czRNQ3JaNzQ2VEFXL1FkeHAva0JxQ3BsQTJSNEJZ\n" +
            "UXVDSC84RjR0Q0FXRTBEL1ZPaDhKbXB3RTlnNlN2OG91ekVRcy9zWWs5ekxxV0xFOFFrdVZOQjNU\n" +
            "T3Z1K1pjaGozYkE3cktUTzlLY2E5NlVrUTNnYStObnVqNWh0SkRPMlhtODhKdHpDeE1uTGxabUlG\n" +
            "NGpESUlIZzBlZXZJTndnb0t3b1VOMjIxYXd1MWczVi9nYXhFWDdxVVF2cHRiUEpTakFKWDB6aFNL\n" +
            "N3N2OCtaamw5SDhMWUJYQ09kVWhCY25vMXM2QjFhb2RwME9acnFWZDZNNGZhU1MveDFtczdzSEJM\n" +
            "ZnBzUTZuMmM0Z2R0WFVFeStGSldGaVhtQmcvVVJLNUhjUEpvSW10aTd2TXZsSWtZTGpxUkNDaW1w\n" +
            "STY5VGI5QmJEOWxDc1F3VVBGTlhmMGdZalhpb1MzVjdsWHZxWDlGVVY3Z0FDQzJpZG9xWEQ5LzZs\n" +
            "SFk1bHkySDcwRHRUSWphbnVzMEd2Q2pDaTYxakk3bVlYR25Ta2x2dDRya2JiZjJ6Y1hOM0F6VnJt\n" +
            "SmdOb3BMc0lianFGaWdGUEZHVmhvemRPWGhVRUtjQ0o1Z2dVTFlsQi90NEphakluRmt2V1pnblBn\n" +
            "S0lHaG9VOEkzSGwzUDlNbElKTTQ4bHpVNzhDa0ZBYnU4M0lsUEhQY3E2YWFxa3J2M1JxL095Zjd5\n" +
            "NVgvUkNmdXRWQ3lhc3ZUalBvcVpFZ2pwV1ZjRVBBQXdiWlo4M1JzVWtrZy9TRzljdWh1dkd0M1V3\n" +
            "WkRXVzZpSUN3TS9FbW9jMFY4PTwvZHM6U2lnbmF0dXJlVmFsdWU+PGRzOktleUluZm8+PGRzOlg1\n" +
            "MDlEYXRhPjxkczpYNTA5Q2VydGlmaWNhdGU+TUlJRjZqQ0NBOUtnQXdJQkFnSUJJVEFOQmdrcWhr\n" +
            "aUc5dzBCQVFVRkFEQ0JrakVMTUFrR0ExVUVCaE1DUWtjeEV6QVJCZ05WQkFnVApDbE52Wm1saExX\n" +
            "ZHlZV1F4RGpBTUJnTlZCQWNUQlZOdlptbGhNUm93R0FZRFZRUUtFeEZDZFd4VGFTMUVaWFpsYkc5\n" +
            "d2JXVnVkREVMCk1Ba0dBMVVFQ3hNQ1NWUXhEakFNQmdOVkJBTVRCVUoxYkZOcE1TVXdJd1lKS29a\n" +
            "SWh2Y05BUWtCRmhaalpYSjBhV1pwWTJGMFpYTkEKWW5Wc0xYTnBMbUpuTUI0WERURTJNREV3TkRF\n" +
            "ME16Y3dNRm9YRFRJMU1ERXdOREUwTXpjd01Gb3dnWXd4Q3pBSkJnTlZCQVlUQWtKSApNUk13RVFZ\n" +
            "RFZRUUlFd3BUYjJacFlTMW5jbUZrTVE0d0RBWURWUVFIRXdWVGIyWnBZVEVOTUFzR0ExVUVDaE1F\n" +
            "WldkdmRqRU1NQW9HCkExVUVDeE1EYzNSek1SUXdFZ1lEVlFRREV3dHpkSE11WldkdmRpNWlaekVs\n" +
            "TUNNR0NTcUdTSWIzRFFFSkFSWVdZMlZ5ZEdsbWFXTmgKZEdWelFHSjFiQzF6YVM1aVp6Q0NBaUl3\n" +
            "RFFZSktvWklodmNOQVFFQkJRQURnZ0lQQURDQ0Fnb0NnZ0lCQU9JRUJlQ1FKdzZ2OUtzSwp4SUh0\n" +
            "anp6WUtpblFTK3lrSnpzaHY1QzJabTFMOGplN3pOb0x1V3QwQkR3ZURpdkZmL21kTFpucVVzNTNo\n" +
            "bCtrTnMrQ216cno0eXJjCnJVdVlsOU5zeTIvSi9EVkRTK3lmVGJLU0NGWFljSkkxS2JLek9mZXhu\n" +
            "SlFxN0lQMUV3TFdjLy9DbWZMb3MvakVPSWJCd2taeHl1NG0KM3QrcDNsWkxnMzNxYVZVOEZ3dGZm\n" +
            "SGlZM2JVdTV5VWRjRE84T25TQkRsQWJmYUZKUzcwT3E4ck9hK0dJMm9DbDNPcUJzU1NRNHp3WApz\n" +
            "Qk1rQjBFR29yY2VCeTJsVWl6VEtldklMU0ErdTNlcGxLWnhiYnpHQU5ycnpDcWtKZTdQbnE5YXZo\n" +
            "ZGlGRzBLZFp0MElpZitPaHJRClRSRlo1ZnhCVXc4SkQ4a1FVazJpRlpWWC9YOFgwSHVYN2JtaEox\n" +
            "N1ExdjhpU0EvVmtxNkpXY2gyQjFsdGpSQzhGamEzSFNSVzM1UFMKZUNxMDdyZG95cVJDbGhhTFA5\n" +
            "YUU2K25RMlVMTGZiN3V1WXRIZmEzblNhVUErMkFnN1BrRFRxUDRCc3RzK2ZDSU1BVzU0OHZjQWNw\n" +
            "YwpyRWFFVis5S2Iva2tuN2x2RVhpeDNBRzY5TElFdDJ0cUhjR3M0aGhhMzFOUjFsamsyTTMvTTRr\n" +
            "N01GNnJ6d0x1eDFZWkp6NVR1OU5kCkh1dDI1alNZOVlENEtaeEdQb1JnSjFwUUF4RllJTFNveGcr\n" +
            "cnF3ZHBESXFMWTlGUDNWMTBCK3NQWDk0dlJLdEhFbTlkWStXazdZQUEKditMZ1FGOFVGMm90NnN5\n" +
            "eURNTXBSK0k3VjZPUVVSSXN3eU1ncUhxMXdOMEpsS0Zwdy9MZWxQNUFhTUpKQWdNQkFBR2pUekJO\n" +
            "TUF3RwpBMVVkRXdFQi93UUNNQUF3SFFZRFZSME9CQllFRkdFYlFjWUQrb3lNU3o5QWNOdlFGb2Uv\n" +
            "Y3NyUE1Bc0dBMVVkRHdRRUF3SUY0REFSCkJnbGdoa2dCaHZoQ0FRRUVCQU1DQmtBd0RRWUpLb1pJ\n" +
            "aHZjTkFRRUZCUUFEZ2dJQkFGeUUzbW50K3dNb0k4dStLWnprTlVZTmRQR3oKN0x4N05STDBONzJi\n" +
            "eFRLaFY3cDZwVHZRZXlmWFBLL1JIVnBrZHpjaGxWcFU5ZnIrTVJGZlYwdk9KTXpEakowNFVEc2Nj\n" +
            "YVdBMjN4SgpmUVpRbXE4Ykh6RmVHeFVGNjZpa05HWGhPenk1dUtqeHZ2VHcrclFWL25ELzdKemFG\n" +
            "THIvWVdheGdPNlRLakFicUZhZ2Q4MUNoY25OCjdsSXo2UDlSQXp1RnN4L05rdzB3aS9ybzZmeUtQ\n" +
            "MzM3V1lHSkxSUWRFd3NCNEFHN3djak9NZWUvbzdvVTNNWUg0Z1BMaHFaTTdPL2QKcDZUSHI3Vk5O\n" +
            "bDZ5TFdHc1dxbnVJbnZISVRudVFPL3ZIaDFIMG52UzNPT3NDOVJkTGlmVThGK3hoUnZXSnU2YXlr\n" +
            "N1J5RjJ4QVhTOQpPUjVTeG1lVUU3RWhaZENxN0RBbHE4a3pnOGdNVUVvVm5saGsvUkZzdEpCT1A4\n" +
            "NUJZT2VTOFJWT21hMm5qa2kvVXFtZnkrTG41TURyCjlVd3JnaXBJVlZKdTFNWTU4citSbzUxaG16\n" +
            "K0QrR21mSkhMZjJYMGpzVTBXOEtOdkRkK2Y3WXIwVGxlNVpJOW5ybmZnWkxiamFyN0sKcWk3aEh0\n" +
            "ZUdrUU5mM1lObVJDSVJkc2pwNitsNWxFY2FlTUlwUk0rKy8ybjl1cUZLcU11WTRQYUZhREVJNWpG\n" +
            "MTJmekk1QXdCQk56cgpWVFBpc1NrSWc3UjNRd0pEK3F0WnpNS1YrY2I4alJBcTczZGVMcWxnTWFj\n" +
            "eCtRTklTY0R6aWdwb0t3dHExeHAwTjd1V2FiSlRsODZtCkNnQ0wzUEpiaGdHYlFsLzdkSUVTcGtS\n" +
            "MDN5YXpMZEJzTllhcnBDYnh3c21PbjVRdTwvZHM6WDUwOUNlcnRpZmljYXRlPjwvZHM6WDUwOURh\n" +
            "dGE+PC9kczpLZXlJbmZvPjwvZHM6U2lnbmF0dXJlPjxzYW1sMjpTdWJqZWN0PjxzYW1sMjpOYW1l\n" +
            "SUQgRm9ybWF0PSJ1cm46b2FzaXM6bmFtZXM6dGM6U0FNTDoyLjA6YXR0cm5hbWUtZm9ybWF0OnVy\n" +
            "aSIgTmFtZVF1YWxpZmllcj0idXJuOmVnb3Y6Ymc6ZWF1dGg6MS4wOmF0dHJpYnV0ZXM6ZUlkZW50\n" +
            "aWZpZXI6RUdOIj44ODA4MDE2MjQ0PC9zYW1sMjpOYW1lSUQ+PHNhbWwyOlN1YmplY3RDb25maXJt\n" +
            "YXRpb24gTWV0aG9kPSJ1cm46b2FzaXM6bmFtZXM6dGM6U0FNTDoyLjA6Y206c2VuZGVyLXZvdWNo\n" +
            "ZXMiPjxzYW1sMjpTdWJqZWN0Q29uZmlybWF0aW9uRGF0YS8+PC9zYW1sMjpTdWJqZWN0Q29uZmly\n" +
            "bWF0aW9uPjwvc2FtbDI6U3ViamVjdD48c2FtbDI6Q29uZGl0aW9ucyBOb3RCZWZvcmU9IjIwMTgt\n" +
            "MTEtMTRUMTM6NTU6NDguMzUzWiIgTm90T25PckFmdGVyPSIyMDE4LTExLTI4VDEzOjU1OjQ4LjM1\n" +
            "M1oiPjxzYW1sMjpBdWRpZW5jZVJlc3RyaWN0aW9uPjxzYW1sMjpBdWRpZW5jZT51cm46ZWdvdjpi\n" +
            "ZzpzZXJ2aWNlczpvaWQ6Mi4xNi4xMDAuMS4xLjEuMS44LjMuMS4xPC9zYW1sMjpBdWRpZW5jZT48\n" +
            "L3NhbWwyOkF1ZGllbmNlUmVzdHJpY3Rpb24+PC9zYW1sMjpDb25kaXRpb25zPjxzYW1sMjpBdXRo\n" +
            "blN0YXRlbWVudCBBdXRobkluc3RhbnQ9IjIwMTgtMTEtMTRUMTM6NTU6NDguMzQ2WiI+PHNhbWwy\n" +
            "OkF1dGhuQ29udGV4dD48c2FtbDI6QXV0aG5Db250ZXh0Q2xhc3NSZWY+dXJuOm9hc2lzOm5hbWVz\n" +
            "OnRjOlNBTUw6Mi4wOmFjOmNsYXNzZXM6WDUwOTwvc2FtbDI6QXV0aG5Db250ZXh0Q2xhc3NSZWY+\n" +
            "PC9zYW1sMjpBdXRobkNvbnRleHQ+PC9zYW1sMjpBdXRoblN0YXRlbWVudD48c2FtbDI6QXR0cmli\n" +
            "dXRlU3RhdGVtZW50PjxzYW1sMjpBdHRyaWJ1dGUgTmFtZT0idXJuOmVnb3Y6Ymc6ZWF1dGg6MS4w\n" +
            "OmF0dHJpYnV0ZXM6cGVyc29uTmFtZXNMYXRpbiIgTmFtZUZvcm1hdD0idXJuOmVnb3Y6Ymc6ZWF1\n" +
            "dGg6MS4wOmF0dHJpYnV0ZXM6cGVyc29uTmFtZXNMYXRpbiI+PHNhbWwyOkF0dHJpYnV0ZVZhbHVl\n" +
            "IHhzaTp0eXBlPSJ4czpzdHJpbmciPlRvZG9yIEJveWtvdiBWZWx5b3Y8L3NhbWwyOkF0dHJpYnV0\n" +
            "ZVZhbHVlPjwvc2FtbDI6QXR0cmlidXRlPjxzYW1sMjpBdHRyaWJ1dGUgTmFtZT0idXJuOmVnb3Y6\n" +
            "Ymc6ZWF1dGg6MS4wOmF0dHJpYnV0ZXM6ZU1haWwiIE5hbWVGb3JtYXQ9InVybjplZ292OmJnOmVh\n" +
            "dXRoOjEuMDphdHRyaWJ1dGVzOmVNYWlsIj48c2FtbDI6QXR0cmlidXRlVmFsdWUgeHNpOnR5cGU9\n" +
            "InhzOnN0cmluZyI+dG9kb3JfdmVseW92QGJ1bC1zaS5iZzwvc2FtbDI6QXR0cmlidXRlVmFsdWU+\n" +
            "PC9zYW1sMjpBdHRyaWJ1dGU+PC9zYW1sMjpBdHRyaWJ1dGVTdGF0ZW1lbnQ+PC9zYW1sMjpBc3Nl\n" +
            "cnRpb24+PHNhbWxwOkV4dGVuc2lvbnM+PGVnb3ZiZ2E6U1BDb250ZXh0Lz48L3NhbWxwOkV4dGVu\n" +
            "c2lvbnM+PC9zYW1scDpSZXNwb25zZT4=";

    public static String relayStateBase64 = "QXVkaXRMb2cgUmVwb3J0IFBEQUEgQXBwbGljYXRpb24=";

    public static String pid = "8808016244";
    public static String name = "Todor Velyov";
    public static String email = "todor_velyov@bul-si.bg";

}
