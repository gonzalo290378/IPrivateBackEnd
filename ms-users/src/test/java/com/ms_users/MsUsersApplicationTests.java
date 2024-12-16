package com.ms_users;

import com.ms_users.models.entity.User;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = {User.class})
@ExtendWith(SpringExtension.class)
class MsUsersApplicationTests {


}
