package com.example.noticePrj.mappers;

import com.example.noticePrj.dto.ResponseNoticeDTO;
import com.example.noticePrj.dto.SearchDTO;
import com.example.noticePrj.valid.ValidNotice;
import com.example.noticePrj.vo.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper {
    List<Notice> findAll(SearchDTO pagingDTO);

    List<Notice> findAll(ResponseNoticeDTO responseNoticeDTO);

    void createNotice(ValidNotice validNotice);

    Notice findNotice(Long noticeId);

    void updateNotice(ValidNotice validNotice);

    void deleteNotice(Long id);

    void increaseViewCount(Long noticeId);

    int getNoticeCount(String keyword);
}
