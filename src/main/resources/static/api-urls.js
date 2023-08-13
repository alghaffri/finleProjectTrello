export const HTTP_METHOD_GET_NO_CACHE = {
    method: 'GET', headers: { 'Cache-Control': 'no-cache' }
}

//
export function HTTP_METHOD_POST_NO_CACHE(jsonRequestObj) {

    const httpObj = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Cache-Control': 'no-cache'
        },
        // your expected POST request payload goes here
        body: JSON.stringify(jsonRequestObj)
    };
    return httpObj;
}


export function HTTP_REQUEST_NO_CACHE(method,jsonRequestObj) {

    const httpObj = {
        method: method,
        headers: {
            'Content-Type': 'application/json',
            'Cache-Control': 'no-cache'
        },
        // your expected POST request payload goes here
        body: JSON.stringify(jsonRequestObj)
    };
    return httpObj;
}

const host = window.location.host;
const BASE_URL = "http://" + host + ":8080"


export const API_URL_INSERT_CARD = "http://" + host + ":8080/api/boards/1/cards"
export const API_URL_GET_ALL_CARDS = "http://" + host + ":8080/api/boards/1/cards"
export const API_URL_GET_SINGLE_CARD = "http://" + host + ":8080/api/boards/1/cards/"
export const API_URL_UPDATE_CARD = "http://" + host + ":8080/api/boards/1/cards/"
export const API_URL_DELETE_CARD = "http://" + host + ":8080/api/boards/1/cards/"