-- 秒杀执行的存储过程
DELIMITER $$   -- 将换行符由;改为$$
-- 定义存储过程
-- 参数中的IN代表输入参数，OUT代表输出参数
-- BEGIN 是开始存储过程；START TRANSACTION 是开启一个事务；
-- row_count()的作用是：返回上一条修改类型sql（delete，insert，update）的影响行数
-- row_count()函数的返回结果：0 代表未修改数据 ； >0 表示修改的行数 ；<0 表示sql错误/未执行修改sql

CREATE PROCEDURE execute_seckill
  (IN v_seckill_id BIGINT,IN v_phone BIGINT,IN v_kill_time TIMESTAMP,OUT r_result INT)
    BEGIN
    DECLARE insert_count INT DEFAULT 0;
    START TRANSACTION;
    INSERT IGNORE INTO success_killed
      (seckill_id,user_phone,create_time)
      VALUES (v_seckill_id,v_phone,v_kill_time);
    SELECT row_count() INTO insert_count;
    IF (insert_count = 0) THEN
      ROLLBACK ;
      SET r_result = -1;
    ELSEIF(insert_count<0) THEN
      ROLLBACK ;
      SET r_result = -2;
    ELSE
      UPDATE seckill
        SET number = number - 1
      WHERE seckill_id = v_seckill_id
      AND end_time > v_kill_time
      AND start_time < v_kill_time
      AND number > 0;
      SELECT row_count() INTO insert_count;
      IF (insert_count = 0) THEN
        ROLLBACK ;
        SET r_result = -1;
      ELSEIF(insert_count<0) THEN
        ROLLBACK ;
        SET r_result = -2;
      ELSE
        COMMIT ;
        set r_result = 1;
      END IF ;
    END IF ;
  END;
$$
-- 存储过程定义结束


DELIMITER ;
SET @r_result = -3;
-- 执行存储过程
CALL execute_seckill(1025,18862141550,now(),@r_result);
-- 获取结果
SELECT @r_result;

-- 存储过程：
-- 1.存储过程优化：优化事务行级锁的持有时间
-- 2.不要过度依赖存储过程
-- 3.简单的逻辑可以应用存储过程
-- 4.QPS：一个秒杀单接近6000/qps
