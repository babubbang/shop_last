package inhatc.spring.shop.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(value = {AuditingEntityListener.class})  //리스너 = 이벤트가 발생하면 가로챔, JPA에게 해당 엔티티는 Auditing 기능을 사용한다는 것을 알림
@MappedSuperclass   //JPA Entity 클래스들이 BaseTimeEntity를 상속할 경우 필드들도 칼럼으로 인식하도록 함
@Setter
@Getter
public abstract class BaseEntity extends BaseTimeEntity{

    @CreatedBy
    @Column(updatable = false)
    private String createBy;    //등록자

    @LastModifiedBy
    private String modifyBy;    //수정자
}


