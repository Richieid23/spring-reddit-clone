package id.web.fitrarizki.spring_reddit_clone.service;

import id.web.fitrarizki.spring_reddit_clone.dto.SubredditDto;
import id.web.fitrarizki.spring_reddit_clone.exception.SpringRedditException;
import id.web.fitrarizki.spring_reddit_clone.mapper.SubredditMapper;
import id.web.fitrarizki.spring_reddit_clone.model.Subreddit;
import id.web.fitrarizki.spring_reddit_clone.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SubredditService {
    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        Subreddit subreddit = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
        subredditDto.setId(subreddit.getId());
        return subredditDto;
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(subredditMapper::mapSubredditToDto)
                .collect(Collectors.toList());
    }

    public SubredditDto getSubredditById(Long id) {
        Subreddit subreddit = subredditRepository.findById(id).orElseThrow(() -> new SpringRedditException("No subreddit found with id "+id));
        return subredditMapper.mapSubredditToDto(subreddit);
    }
}
