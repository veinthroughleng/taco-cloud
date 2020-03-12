// This api will come in the next version

import { AuthConfig } from 'angular-oauth2-oidc';
import {Constants} from "../utils/Constants";

export const authPasswordFlowConfig: AuthConfig = {
  requireHttps: false,
  tokenEndpoint: Constants.URL_ACCESS_TOKEN,
  // loginUrl: Constants.URL_ACCESS_TOKEN,
  // Url of the Identity Provider
  // issuer: 'https://steyer-identity-server.azurewebsites.net/identity',

  // URL of the SPA to redirect the user to after login
  // redirectUri: window.location.origin + '/index.html',
  redirectUri: Constants.URL_REDIRECT,

  // URL of the SPA to redirect the user after silent refresh
  // silentRefreshRedirectUri: window.location.origin + '/silent-refresh.html',

  // The SPA's id. The SPA is registerd with this id at the auth-server
  clientId: 'web-spa',

  dummyClientSecret: '123456',

  // set the scope for the permissions the client should request
  // The first three are defined by OIDC. The 4th is a usecase-specific one
  scope: 'read write',

  showDebugInformation: true,

  oidc: false
};
