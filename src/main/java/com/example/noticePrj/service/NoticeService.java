package com.example.noticePrj.service;

import com.example.noticePrj.mappers.MemberMapper;
import com.example.noticePrj.mappers.NoticeMapper;
import com.example.noticePrj.valid.ValidNotice;
import com.example.noticePrj.vo.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeMapper noticeMapper;

    public List<Notice> findAll(){
        return noticeMapper.findAll();
    }

    public void createNotice(ValidNotice validNotice) {
        noticeMapper.createNotice(validNotice);
    }

    @Transactional
    public Notice findNotice(Long noticeId) {
        noticeMapper.increaseViewCount(noticeId);
        return noticeMapper.findNotice(noticeId);
    }

    public void updateNotice(ValidNotice validNotice) {
        noticeMapper.updateNotice(validNotice);
    }

    public void deleteNotice(Long noticeId) {
        noticeMapper.deleteNotice(noticeId);
    }
}
