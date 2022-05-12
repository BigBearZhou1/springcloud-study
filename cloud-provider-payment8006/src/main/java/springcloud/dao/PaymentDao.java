package springcloud.dao;

import com.zwz.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PaymentDao {
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into payment(serial) values(#{serial})")
    int create(Payment payment);

    @Select("select * from payment where id=#{id}")
    Payment getPaymentById(Long id);
}
