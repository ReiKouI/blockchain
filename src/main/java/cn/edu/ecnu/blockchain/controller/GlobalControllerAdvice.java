package cn.edu.ecnu.blockchain.controller;

import cn.edu.ecnu.blockchain.exception.GlobalException;
import cn.edu.ecnu.blockchain.result.CodeMessage;
import cn.edu.ecnu.blockchain.result.Result;
import cn.edu.ecnu.blockchain.util.ResultUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ValidationException;

@RestControllerAdvice
@Log4j2
public class GlobalControllerAdvice {



    @ExceptionHandler(Exception.class)
    public Result handleException(Exception ex) {

        if (ex instanceof GlobalException) {
            GlobalException globalException = (GlobalException) ex;
            log.error(globalException.getCodeMessage().getMsg());
            return ResultUtil.error(globalException.getCodeMessage());
        } else {
            ex.printStackTrace();
            log.error(ex.getMessage());
            return ResultUtil.error(CodeMessage.SERVER_ERROR);
        }

    }


}