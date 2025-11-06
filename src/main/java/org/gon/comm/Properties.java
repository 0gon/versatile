package org.gon.comm;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Properties {

    /**
     * 서버 번호
     * 001 부터 999 까지 할당 가능
     * 문자열인 이유는 001, 002 와 같이 앞에 0이 붙을 수 있기 때문
     */
    @Getter
    private static String serverNo;

    protected Properties (@Value("${server.no}") String serverNo) {
        long no = Long.parseLong(serverNo);
        if(no < 1 || no > 999) {
            throw new IllegalArgumentException("server.no는 001부터 999까지의 값이어야 합니다.");
        }

        Properties.serverNo = String.format("%03d", no);
    }

}
