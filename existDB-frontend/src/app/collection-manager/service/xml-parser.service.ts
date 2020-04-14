import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class XmlParserService {

  constructor() { }

  public static isParseError(parsedDocument, parser) {
    const errorNeoUsParse = parser.parseFromString('<', 'text/xml');
    const parserErrorNS = errorNeoUsParse.getElementsByTagName('parsererror')[0].namespaceURI;
    if (parserErrorNS === 'http://www.w3.org/1999/xhtml') {
      return parsedDocument.getElementsByTagName('parsererror').length > 0;
    }
    return parsedDocument.getElementsByTagNameNS(parserErrorNS, 'parsererror').length > 0;
  }

  public static validateXML(input: string): string {
    const domParser = new DOMParser();
    const xmlDoc = domParser.parseFromString(input, 'application/xml');
    if (XmlParserService.isParseError(xmlDoc, domParser)) {
      return xmlDoc.getElementsByTagName('parsererror')[0].getElementsByTagName('div')[0].innerHTML;
    } else {
      return 'isXML';
    }
  }
}
