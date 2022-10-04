package hello.aop.exam;

import hello.aop.exam.annotation.Trace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class ExamRepository {
    private static int seq = 0;

    @Trace
    public String request(String itemId) {

        seq++;
        if (seq % 5 == 0) {
            throw new IllegalStateException("예외발생");
        }

        return "ok";
    }
}
