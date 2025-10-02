package com.sparta.team5.fractal.domain.search.service;

import com.sparta.team5.fractal.domain.search.entity.Search;
import com.sparta.team5.fractal.domain.search.repository.SearchRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    private SearchRepository searchRepository;

    @InjectMocks
    private SearchService searchService;

    @Test
    @DisplayName("검색 키워드 존재 및 조회수 증가 성공")
    void existAndIncrease_Success() {
        // given
        String keyword = "갤럭시";
        Search existingSearch = Search.of(keyword);
        ReflectionTestUtils.setField(existingSearch, "id", 1L);
        ReflectionTestUtils.setField(existingSearch, "count", 5L);
        ReflectionTestUtils.setField(existingSearch, "createdAt", LocalDateTime.now());

        when(searchRepository.findByKeyword(keyword)).thenReturn(existingSearch);

        // when
        boolean result = searchService.existAndIncrease(keyword);

        // then
        assertThat(result).isTrue();
        assertThat(existingSearch.getCount()).isEqualTo(6L);

        verify(searchRepository).findByKeyword(keyword);
    }

    @Test
    @DisplayName("검색 키워드 존재하지 않음")
    void existAndIncrease_KeywordNotFound() {
        // given
        String keyword = "존재하지않는키워드";

        when(searchRepository.findByKeyword(keyword)).thenReturn(null);

        // when
        boolean result = searchService.existAndIncrease(keyword);

        // then
        assertThat(result).isFalse();

        verify(searchRepository).findByKeyword(keyword);
    }

    @Test
    @DisplayName("새로운 검색 키워드 생성 성공")
    void createSearch_Success() {
        // given
        String keyword = "새로운키워드";
        Search newSearch = Search.of(keyword);
        ReflectionTestUtils.setField(newSearch, "id", 1L);
        ReflectionTestUtils.setField(newSearch, "createdAt", LocalDateTime.now());

        when(searchRepository.save(any(Search.class))).thenReturn(newSearch);

        // when
        searchService.createSearch(keyword);

        // then
        verify(searchRepository).save(any(Search.class));
    }

    @Test
    @DisplayName("인기 검색어 TOP 10 조회 성공")
    void getTopTenKeywords_Success() {
        // given
        List<String> topKeywords = List.of(
                "갤럭시", "아이폰", "맥북", "에어팟", "아이패드",
                "삼성", "애플", "노트북", "스마트폰", "태블릿"
        );

        when(searchRepository.getKeywordByCountTopTen(any(Pageable.class))).thenReturn(topKeywords);

        // when
        List<String> result = searchService.getTopTenKeywords();

        // then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(10);
        assertThat(result).containsExactlyElementsOf(topKeywords);

        verify(searchRepository).getKeywordByCountTopTen(any(Pageable.class));
    }

    @Test
    @DisplayName("인기 검색어 TOP 10 조회 - 빈 결과")
    void getTopTenKeywords_EmptyResult() {
        // given
        List<String> emptyList = List.of();

        when(searchRepository.getKeywordByCountTopTen(any(Pageable.class))).thenReturn(emptyList);

        // when
        List<String> result = searchService.getTopTenKeywords();

        // then
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();

        verify(searchRepository).getKeywordByCountTopTen(any(Pageable.class));
    }

    @Test
    @DisplayName("검색 키워드 조회수 증가 - 여러 번 호출")
    void existAndIncrease_MultipleCalls() {
        // given
        String keyword = "테스트키워드";
        Search search = Search.of(keyword);
        ReflectionTestUtils.setField(search, "id", 1L);
        ReflectionTestUtils.setField(search, "count", 1L);

        when(searchRepository.findByKeyword(keyword)).thenReturn(search);

        // when
        boolean result1 = searchService.existAndIncrease(keyword);
        boolean result2 = searchService.existAndIncrease(keyword);
        boolean result3 = searchService.existAndIncrease(keyword);

        // then
        assertThat(result1).isTrue();
        assertThat(result2).isTrue();
        assertThat(result3).isTrue();
        assertThat(search.getCount()).isEqualTo(4L);

        verify(searchRepository, times(3)).findByKeyword(keyword);
    }

    @Test
    @DisplayName("검색 키워드 생성 후 조회수 증가")
    void createSearch_ThenExistAndIncrease() {
        // given
        String keyword = "새키워드";
        Search newSearch = Search.of(keyword);
        ReflectionTestUtils.setField(newSearch, "id", 1L);
        ReflectionTestUtils.setField(newSearch, "count", 1L);

        when(searchRepository.save(any(Search.class))).thenReturn(newSearch);
        when(searchRepository.findByKeyword(keyword)).thenReturn(newSearch);

        // when
        searchService.createSearch(keyword);
        boolean result = searchService.existAndIncrease(keyword);

        // then
        assertThat(result).isTrue();
        assertThat(newSearch.getCount()).isEqualTo(2L);

        verify(searchRepository).save(any(Search.class));
        verify(searchRepository).findByKeyword(keyword);
    }
}
