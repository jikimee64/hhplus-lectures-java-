package hhplus.lectures.application;

import hhplus.lectures.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;
    private final LectureRegistrationRepository lectureRegistrationRepository;
    private final LectureRegistrations lectureRegistrations;

    @Retryable(
            retryFor = {ObjectOptimisticLockingFailureException.class},
            maxAttempts = 1000,
            backoff = @Backoff(delay = 100)
    )
    @Transactional
    public void apply(long lectureId, long userId) {
        LectureRegistration lectureRegistration = lectureRegistrations.register(lectureId, userId);
        lectureRegistrationRepository.save(lectureRegistration);
    }

    @Transactional(readOnly = true)
    public boolean hasUserAppliedForLecture(long lectureId, long userId) {
        List<LectureRegistration> lectureRegistrations = lectureRegistrationRepository.findBy(lectureId, userId);
        return !lectureRegistrations.isEmpty();
    }

    @Transactional(readOnly = true)
    public List<Lecture> selectLectures() {
        return lectureRepository.findAll();
    }
}
