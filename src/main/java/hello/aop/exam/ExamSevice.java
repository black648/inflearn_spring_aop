package hello.aop.exam;

import hello.aop.exam.annotation.Trace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExamSevice {
    private final ExamRepository examRepository;

    @Trace
    public void request(String itemId) {
        examRepository.request(itemId);
    }
}
