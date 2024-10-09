package cm.pep.timeTable.domain.user;


import cm.pep.timeTable.util.data.UserData;

public interface UserFactory {
   UserEvent registerUser(UserData userData);

   UserEvent loginUser(UserData userData);
}
