package com.ootd.with.domain.hashtag;

import com.ootd.with.domain.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HashtagServiceImpl implements HashtagService {

    private final HashtagRepository hashtagRepository;

    /**
     * post 에 hashtag 등록 시 사용될 hashtag 조회
     * null 반환될 경우 해당 name 으로 hashtag 등록하여 사용
     */
    @Override
    public Hashtag findByName(String name) {
        // 사용하는 곳에서 존재하지 않는 경우 (null 일 경우) 조건문 넣어주기
        return hashtagRepository.findByName(name).orElse(null);
    }

    /**
     * post 에 hashtag 등록 시 사용될 hashtag 등록
     * 해당 name 으로 조회되는 hashtag 가 없을 경우 등록하여 사용
     */
    @Override
    public Hashtag save(Hashtag hashtag) {
        // hashtag 와 post 의 객체 레벨 연결은 post 등록 시에 하는 것으로
        return hashtagRepository.save(hashtag);
    }

    /**
     * post 조회 시 사용될 해당 post 의 모든 hashtag 조회
     */
    @Override
    public List<Hashtag> findAllByPostId(Long postId) {
        return hashtagRepository.findAllByPostId(postId);
    }

    /**
     * hashtag 검색 시 사용될 Post 목록 조회
     * 해당 hashtag 를 가진 모든 Post 를 조회 (Paging)
     */
    @Override
    public Page<Post> findPostsByHashtagId(Long hashtagId, int page, int size) {
        Hashtag hashtag = hashtagRepository.findById(hashtagId).orElse(null);

        // null 받으면 해당 hashtag 없음. 호출부에서 예외처리
        if(hashtag == null) {
            return null;
        }

        // 페이지 비었을 경우 호출부에서 예외처리 필요
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        return hashtagRepository.findPostsByHashtagId(hashtagId, pageRequest);
    }
}
