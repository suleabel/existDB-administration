import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class XmlParserService {

  constructor() { }

  public static isParseError(parsedDocument, parser) {
    const errorneousParse = parser.parseFromString('<', 'text/xml');
    const parsererrorNS = errorneousParse.getElementsByTagName('parsererror')[0].namespaceURI;
    if (parsererrorNS === 'http://www.w3.org/1999/xhtml') {
      return parsedDocument.getElementsByTagName('parsererror').length > 0;
    }
    return parsedDocument.getElementsByTagNameNS(parsererrorNS, 'parsererror').length > 0;
  }

  public validateXML(input: string): string {
    const domparser = new DOMParser();
    const xmlDoc = domparser.parseFromString(input, 'application/xml');
    if (XmlParserService.isParseError(xmlDoc, domparser)) {
      return xmlDoc.getElementsByTagName('parsererror')[0].getElementsByTagName('div')[0].innerHTML;
    } else {
      return 'isXML';
    }
  }
}
