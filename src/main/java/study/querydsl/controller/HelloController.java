package study.querydsl.controller;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;
import study.querydsl.repository.MemberRepository;

import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
public class HelloController {

    @Autowired
    EntityManager em;



    @GetMapping("/hello")
    public String hello(){
        return "바뀌거라..";
    }

//    @PostMapping("")
//    @ResponseStatus(HttpStatus.CREATED)
//    public List<String> upload(@RequestPart List<MultipartFile> files,
//                               @RequestParam String field1,
//                               @RequestParam String field2,
//                               @RequestParam String field3,
//                               @RequestParam String field4) throws Exception {
//        // TODO
//    }

    @PostMapping("/upload/imagesingle")
    public String uploadSingle(@RequestParam("nickname") String nickname,@RequestParam("area") String area,@RequestParam("files") List<MultipartFile> files) throws Exception {
        String basePath = "C:/study/querydsl/src/main/resources/static/upload";
        for (MultipartFile file : files) {
            String filePath = basePath + "/" + file.getOriginalFilename();
            File dest = new File(filePath);
            file.transferTo(dest); // 파일 업로드 작업 수행

        }


        return "ok";
    }
//    @PostMapping("/upload/test")
//    public String test(@RequestParam("sj") String sj) throws Exception {
//        String basePath = "C:/study/querydsl/src/main/resources/static/upload";
//
//
//
//        return sj;
//    }


    @PostMapping("/upload/x")
    public String testx(@RequestParam("sj") String sj) throws Exception {
        String basePath = "C:/study/querydsl/src/main/resources/static/upload";

        return sj;
    }


    @Autowired
    private ServletContext servletContext;

    @Transactional
    @PostMapping("/upload/test")
    public String test(@RequestParam("nickname") String nickname,@RequestParam("files") List<MultipartFile> files) throws Exception {

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        Member findMember = queryFactory
                .select(QMember.member)
                .from(QMember.member)
                .where(QMember.member.nickname.eq(nickname))
                .fetchOne();

        String photos = findMember.getPhotos();
        String photos2222 = findMember.getPhotos();

        //String basePath = "/upload";


        //https://ssj9902.tistory.com/entry/Java-%EA%B2%BD%EB%A1%9C-%EA%B5%AC%ED%95%98%EA%B8%B0-%EC%A0%88%EB%8C%80%EA%B2%BD%EB%A1%9C-%EC%83%81%EB%8C%80%EA%B2%BD%EB%A1%9C
        String realPath = "";
//        realPath = new File("").getAbsolutePath()   //상대경로
        realPath = new File("").getCanonicalPath() + "/tomcat/webapps/uploads"  ;  //절대경로


        for (MultipartFile file : files) {
            String filename = file.getOriginalFilename();
            String filePath = realPath + "/" + filename;
            File dest = new File(filePath);
            if(dest.exists()){
                filename = (int)(Math.random()*100000) + "_" + file.getOriginalFilename();
                filePath = realPath + "/" + filename;
                File dest2 = new File(filePath);
                file.transferTo(dest2); // 파일 업로드 작업 수행

            }
            else{
                file.transferTo(dest); // 파일 업로드 작업 수행
            }
            photos = (photos == null || photos.trim() == "" || photos.isEmpty()) ? filename : photos +  "|" + filename;
        }

        findMember.changePhotos(photos);
        em.persist(findMember);
        em.flush();
        em.clear();

        return photos;
    }
}
