

export function isLogin() {
    const accessTk = localStorage.getItem('access-tk');
    return accessTk !== null && accessTk !== '';
}

export function getUsername() {
    // 쿠키에서 username 값을 가져오는 함수
    const name = 'username=';
    const decodedCookie = decodeURIComponent(document.cookie);
    const ca = decodedCookie.split(';');
    for (let i = 0; i < ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) === ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) === 0) {
            return c.substring(name.length, c.length);
        }
    }
    return null;
}

/**
 * 쿠키 설정 함수
 * @param {string} name
 * @param {data} value
 * @param {number} days 
 */
function setCookie(name, value, days) {
    const d = new Date();
    d.setTime(d.getTime() + (days * 24 * 60 * 60 * 1000));
    const expires = "expires=" + d.toUTCString();
    document.cookie = name + "=" + value + ";" + expires + ";path=/";
}