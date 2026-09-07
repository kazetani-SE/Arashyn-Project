package com.arashi.edu.arashynbe.shared.ownership;

import com.arashi.edu.arashynbe.entity.auth.Account;

public interface OwnedEntity {

  Account getOwner();

}