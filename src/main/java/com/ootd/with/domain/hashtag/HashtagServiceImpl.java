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

    @Override
    public Hashtag save(String hashtagName) {
        Hashtag findHashtag = hashtagRepository.findByName(hashtagName).orElse(null);
        if(findHashtag == null) {
            Hashtag hashtag = Hashtag.builder()
                    .name(hashtagName)
                    .build();
            // 이 시점에서는 post hashtag 와의 연관관계 편의 맞출 필요 없다
            // hashtag 등록만 했을 뿐 아직 post 에 연결은 하기 전임
            // post 등록에서 이 메서드를 호출한 이후 post hashtag 를 만들 때 맞춰준다
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
