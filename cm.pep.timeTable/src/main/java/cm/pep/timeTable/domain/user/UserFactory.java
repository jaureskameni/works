package cm.pep.timeTable.domain.user;


import cm.pep.timeTable.util.data.UserData;

public interface UserFactory {
   User registerUser(UserData userData);

   User loginUser(UserData userData);
}
