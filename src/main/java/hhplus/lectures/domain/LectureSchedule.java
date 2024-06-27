package hhplus.lectures.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long lectureId;

    private int limitedCount;

    private int registeredCount;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    @Version
    private Long version;

    public LectureSchedule(Long lectureId, int limitedCount, int registeredCount, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.lectureId = lectureId;
        this.limitedCount = limitedCount;
        this.registeredCount = registeredCount;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public boolean isOverStartDateTime() {
        return LocalDateTime.now().isAfter(startDateTime);
    }

    public void increaseRegisterCount() {
        if(this.limitedCount <= this.registeredCount){
            throw new IllegalArgumentException("신청 가능한 인원을 초과하였습니다.");
        }
        this.registeredCount++;
    }

    public Long getId() {
        return id;
    }

    public Long getLectureId() {
        return lectureId;
    }

    public int getLimitedCount() {
        return limitedCount;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }
}
