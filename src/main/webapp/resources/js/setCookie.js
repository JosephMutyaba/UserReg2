// setCookie.js

function setUserCookie(username) {
    document.cookie = "selectedUser=" + username + "; path=/";
}

function clearUserCookie() {
    document.cookie = "selectedUser=; expires=Thu, 01 Jan 1970 00:00:00 GMT; path=/";
}

function setDependantIdCookie(d_id) {
    document.cookie = "dependantId=" + d_id + "; path=/";
}

function clearSetDependantIdCookie() {
    document.cookie = "dependantId=; expires=Thu, 01 Jan 1970 00:00:00 GMT; path=/";
}