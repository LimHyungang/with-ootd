package com.ootd.with.domain.hashtag;

import com.ootd.with.domain.post.Post;
import com.ootd.with.domain.post.PostHashtag;
import com.ootd.with.domain.post.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class HashtagRepositoryTest {

    @Autowired EntityManager em;
    @Autowired PostRepository postRepository;
    @Autowired HashtagRepository hashtagRepository;

//    @Test
//    public void findAllByPostIdTest() {
//        // given
//        Hashtag hashtag = new Hashtag("hashtag");
//        hashtagRepository.save(hashtag);
//
//        Post post = new Post();
//        postRepository.save(post);
//
//        post.getHashtagList().add(new PostHashtag(post, hashtag));
//        em.flush();
//        em.close();
//
//        // when
//        List<Hashtag> hashtagList = hashtagRepository.findAllByPostId(post.getId());
//
//        // then
//        assertThat(hashtagList.size()).isEqualTo(1);
//    }
}