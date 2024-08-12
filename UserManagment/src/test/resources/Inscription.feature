# scenario of inscription user
  Feature: User Inscription

    #-----------------------------------------REGISTER USER------------------------------------------------------#

    Scenario Outline: Successful user registration
      Given I am on the registration page
      When I fill out the registration form with <userName>and <email> and <passWord>
      And I submit my registration form
      Then I receive the confirmation

      Examples:
        | userName | email                  | passWord |
        | jaures   | jaureskameni@gmail.com | jaures06 |


     #-----------------------------------------LOGIN USER------------------------------------------------------#

    Scenario Outline: Successful Login user
      Given I am on the login page
      When  I enter my information with <email> and <passWord>
      And   I submit my login form
      Then  I am redirected to my home page

      Examples:
        | email                  | passWord |
        | jaureskameni@gmail.com | jaures06 |


    Scenario Outline: Error Login user because email and password does not exist
      Given I am on the login page
      When  I enter my information with <email> and <passWord>
      And   I submit my login form
      Then  I receive the message error "User do not exist"

      Examples:
        | email                  | passWord |
        | jaureskameni@gmail.com | jaures06 |

    Scenario Outline: Error Login user because email is correct but password doesn't match
      Given I am on the login page
      When  I enter my information with <email> and <passWord>
      And   I submit my login form
      Then  I receive the message error "password doesn't not match"

      Examples:
        | email                  | passWord |
        | jaureskameni@gmail.com | jaures06 |

    Scenario Outline: Error Login user because email is incorrect but password is correct
      Given I am on the login page
      When  I enter my information with <email> and <passWord>
      And   I submit my login form
      Then  I receive the message error "email is incorrect"

      Examples:
        | email                  | passWord |
        | jaureskameni@gmail.com | jaures06 |


      #----------------------------------------- USER MANAGEMENT ------------------------------------------------------#