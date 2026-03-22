package com.book.bookcore.service;

import com.book.bookcommon.constant.Constants;
import com.book.bookcommon.exception.ServiceException;
import com.book.bookcore.entity.Fine;
import com.book.bookcore.mapper.FineMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FineService {
    @Resource
    private FineMapper fineMapper;

    public int addFine(Fine fine) {
        return fineMapper.insert(fine);
    }

    public Fine payFine(Long id) {
        Fine fine = fineMapper.selectById(id);
        if (fine == null) {
            throw new ServiceException("未查询到执行罚金记录");
        }
        fine.setStatus(Constants.STATUS_PAID);
        fine.setPayTime(LocalDateTime.now());
        int res = fineMapper.updateToPaid(id, LocalDateTime.now());
        if (res != 1) {
            throw new ServiceException("支付罚金失败");
        }
        return fine;
    }

    public Fine getFineById(Long id) {
        Fine fine = fineMapper.selectById(id);
        if (fine == null) {
            throw new ServiceException("未查询到执行罚金记录");
        }
        return fine;
    }

    public List<Fine> getFineByUserId(Long userId) {
        List<Fine> fines = fineMapper.selectByUserId(userId);
        if (fines.isEmpty()) {
            throw new ServiceException("未查询到执行罚金记录");
        }
        return fines;
    }

    public List<Fine> getUnpaidFineByUserId(Long userId) {
        List<Fine> fines = fineMapper.selectUnpaidByUserId(userId);
        if (fines.isEmpty()) {
            throw new ServiceException("未查询到执行罚金记录");
        }
        return fines;
    }
}
