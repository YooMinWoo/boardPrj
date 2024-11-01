package com.example.noticePrj.service;

import com.example.noticePrj.dto.PagingDTO;
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

    public List<Notice> findAll(PagingDTO pagingDTO){
        return noticeMapper.findAll(pagingDTO);
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

    public void dummyData(String member_id){
        for(int i=1; i<=500; i++){
            ValidNotice validNotice = new ValidNotice();
            validNotice.setMember_id(member_id);
            validNotice.setTitle("더미데이터("+i+")");
            validNotice.setContent("더미데이터("+i+")");
            noticeMapper.createNotice(validNotice);
        }
    }
}
