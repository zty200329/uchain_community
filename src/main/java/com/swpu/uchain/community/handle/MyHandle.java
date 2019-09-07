package com.swpu.uchain.community.handle;

import com.swpu.uchain.community.enums.ResultEnum;
import com.swpu.uchain.community.exception.BasicException;
import com.swpu.uchain.community.util.ResultVOUtil;
import com.swpu.uchain.community.vo.ResultVO;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author；lzh
 * @Date:2019/7/1710:28 Descirption:
 */
@ControllerAdvice
public class MyHandle {

    /**
     * 处理异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(BasicException.class)
    public ResultVO handle(BasicException e){
        return ResultVOUtil.error(ResultEnum.ERROR.getCode(),e.getMessage());
    }
}
