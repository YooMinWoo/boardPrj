package com.example.noticePrj.service;

import static org.assertj.core.api.Assertions.*;

import com.example.noticePrj.exception.DuplicateIdException;
import com.example.noticePrj.valid.ValidMember;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;



@Transactional
@SpringBootTest
class MemberServiceTest {
    ValidTest validTest = new ValidTest();

    @Autowired
    private MemberService memberService;


    @Test
    void testSignupSuccess() {
        //given
        boolean success = true;
        ValidMember validMember = ValidMember.builder()
                .id("alsn99")
                .pw("test1234!@")
                .name("유민우")
                .roles("ROLE_USER")
                .build();

        //when
        try {
            validTest.validMember(validMember);
            memberService.signup(validMember);
        } catch (ValidationException validationException){
            success = false;
            System.err.println(validationException.getMessage());
        } catch (DuplicateIdException duplicateIdException){
            success = false;
            System.err.println(duplicateIdException.getMessage());
        } catch (Exception e){
            success = false;
            System.err.println("예상치 못 한 에러 발생!");
        }

        //then
        assertThat(success).isEqualTo(true);
    }

    @Test
    void testIdBinding(){
        try{
            //given
            ValidMember validMember = ValidMember.builder()
                    .id("tt")
                    .pw("test1234!@")
                    .name("유민우")
                    .roles("ROLE_USER")
                    .build();
        } catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    @Test
    void findId() {
        //given

        //when

        //then
    }
}