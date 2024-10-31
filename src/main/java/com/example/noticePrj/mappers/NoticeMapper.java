package com.example.noticePrj.mappers;

import com.example.noticePrj.valid.ValidNotice;
import com.example.noticePrj.vo.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper {
    List<Notice> findAll();

    void createNotice(ValidNotice validNotice);

    Notice findNotice(Long noticeId);

    void updateNotice(ValidNotice validNotice);

    void deleteNotice(Long id);

    void increaseViewCount(Long noticeId);
}
