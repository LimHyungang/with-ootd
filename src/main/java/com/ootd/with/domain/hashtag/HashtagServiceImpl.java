package com.ootd.with.domain.hashtag;

import com.ootd.with.domain.enumtype.StatusType;
import com.ootd.with.domain.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HashtagServiceImpl implements HashtagService {

    private final HashtagRepository hashtagRepository;

    /**
     * post 에 hashtag 등록 시 사용될 hashtag 등록
     * 1. 이미 존재하는 hashtag
     *   -> 그대로 반환
     * 2. 존재하지 않는 hashtag
     *   -> 저장 후 반환
     */
    @Override
    public Hashtag save(Hashtag hashtag) {
        Hashtag findHashtag = hashtagRepository.findByName(hashtag.getName()).orElse(null);
        if(findHashtag == null) {
            // hashtag 와 post 의 객체 레벨 연결은 post 등록 시에 하는 것으로
            return hashtagRepository.save(hashtag);
        }else {
            return findHashtag;
        }
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
    public Page<Post> findPostsByHashtagNameAndStatusType(String hashtagName, StatusType type, Pageable pageable) {
        // 생각해보니.. 굳이 여기서 null 검사할 필요가 없다
        // 해당 name 의 hashtag 가 없으면 페이징 조회해서 그냥 빈 페이지 반환할 것
//        Hashtag hashtag = hashtagRepository.findByName(hashtagName).orElse(null);

        // 호출부에서 빈 페이지에 대한 예외처리도 할 필요 없다
        // 그냥 그대로 view 에 넘겨주면 됨
        return hashtagRepository.findPostsByHashtagNameAndStatusType(hashtagName, StatusType.NORMAL, pageable);
    }
}
