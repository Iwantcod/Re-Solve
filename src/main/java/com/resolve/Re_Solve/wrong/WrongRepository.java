package com.resolve.Re_Solve.wrong;

import com.resolve.Re_Solve.wrong.dto.MailFormatDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface WrongRepository extends CrudRepository<Wrong, Long> {
    @Query("""
        select new com.resolve.Re_Solve.wrong.dto.MailFormatDto(w.title, p.name, w.num, w.date)
        from Wrong w
        join w.platform p
        where w.users.usersId = :usersId
                and w.date in :dates
        """)
    List<MailFormatDto> findWrongByUsersId(@Param("usersId") Long usersId, @Param("dates") List<LocalDate> dates);

    @Query("""
        select count(w) from Wrong w
        where w.users.usersId = :usersId and date(w.createdAt) = :today
    """)
    Long countTodayAddByUsersIdAndDate(@Param("usersId") Long usersId, @Param("today") LocalDate today);
}
