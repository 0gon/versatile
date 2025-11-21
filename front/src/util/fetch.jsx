/**
 * API 응답을 모델링하는 클래스입니다.
 * @template T - 응답 본문(Payload)의 데이터 타입
 */
class Response {

    /**
     * @param {{resultTypeCode: string, success: boolean, message: string, data: T | null}} object
     * 서버 응답 객체
     */
    constructor(object) {
        this.resultTypeCode = object.resultTypeCode;
        this.success = object.success;
        this.message = object.message;
        this.data = object.data;
    }
}

/**
 * 주어진 데이터를 지정된 URL로 POST 요청합니다.
 * @param {string} url - API 엔드포인트 URL
 * @param {object} data - 서버로 보낼 JSON 데이터 객체
 * @returns {Promise<Response>} 서버 응답 데이터 (JSON 객체)
 */
export async function post(url, data) {
    return await fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }).then(handleResponse);
}

/**
 * 주어진 데이터를 지정된 URL로 POST 요청합니다.
 * @param {string} url - API 엔드포인트 URL
 * @param {object} data - 서버로 보낼 JSON 데이터 객체
 * @returns {Promise<Response>} 서버 응답 데이터 (JSON 객체)
 */
export async function postAuth(url, data) {
    return await fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('access-tk')}`
        },
        body: JSON.stringify(data)
    }).then(handleResponse);
}

async function handleResponse(response) {
    if (!response.ok) {
        // HTTP 오류 처리
        const errorBody = await response.json().catch(() => ({}));
        throw new Error(`HTTP Error ${response.status}: ${errorBody.message || 'Server error'}`);
    } else {
        const responseData = await response.json();
        return new Response(responseData);
    }
}