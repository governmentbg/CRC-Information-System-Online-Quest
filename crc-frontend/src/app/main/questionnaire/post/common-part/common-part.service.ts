import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

@Injectable()
export class CommonPartService {

    constructor(private httpClient: HttpClient) {

    }

    getData() {
       return this.httpClient
          .get('./assets/SampleFile.json');
      }
  
}