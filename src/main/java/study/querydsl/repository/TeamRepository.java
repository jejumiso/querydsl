package study.querydsl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.querydsl.entity.Member;
import study.querydsl.entity.Team;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team,Long>{
    List<Team> findByteamname(String teamname);
}
