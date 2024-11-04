package com.example.noticePrj.service;

import com.example.noticePrj.dto.ResponseNoticeDTO;
import com.example.noticePrj.dto.SearchDTO;
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

    public List<Notice> findAll(SearchDTO pagingDTO){
        return noticeMapper.findAll(pagingDTO);
    }

    public List<Notice> findAll(ResponseNoticeDTO responseNoticeDTO){
        return noticeMapper.findAll(responseNoticeDTO);
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

    public int getNoticeCount(String keyword){
        return noticeMapper.getNoticeCount(keyword);
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

    public ResponseNoticeDTO getAllPage(int pageNum, String keyword, int pageSize, String sort) {
        ResponseNoticeDTO responseNoticeDTO = new ResponseNoticeDTO();
        responseNoticeDTO.setPageNum(pageNum);
        responseNoticeDTO.setKeyword(keyword);
        responseNoticeDTO.setPageSize(pageSize);
        responseNoticeDTO.setSort(sort);
        responseNoticeDTO.setTotalEles(getNoticeCount(keyword));
        responseNoticeDTO.setTotalPage(
                (responseNoticeDTO.getTotalEles() + responseNoticeDTO.getPageSize() - 1) / responseNoticeDTO.getPageSize()
        );

        responseNoticeDTO.setNoticeList(findAll(responseNoticeDTO));

        responseNoticeDTO.setLast(responseNoticeDTO.getPageNum() == responseNoticeDTO.getTotalPage());

        return responseNoticeDTO;
    }
}
