import sqlite3
import pandas as pd

connection = sqlite3.connect("Hotel_Management.db")

cursor = connection.cursor()

command1 = """CREATE TABLE IF NOT EXISTS
customer(customer_id INTEGER PRIMARY KEY, Mobile_no INTEGER, First_name TEXT, Last_name Text, Age INT)
"""
cursor.execute(command1)


command2 = """CREATE TABLE IF NOT EXISTS
rooms(room_id INTEGER PRIMARY KEY, customer_id INTEGER, Rate INT, Stay_days INTEGER, Rent INT )
"""
cursor.execute(command2)

command3 = """CREATE TABLE IF NOT EXISTS
food(id INT NOT NULL ,
           rate INT NOT NULL,
           Quantity INT NOT NULL,
           Bill INT)
"""
cursor.execute(command3)

cont = 'y'



while cont == 'y' or cont == 'Y':
    print("\n\t----------Welcome to Hotel Empire Palace----------\n")
    print("Chose from below:\n1)Insert a Customer\n2)Book a room\n3)Order Food\n4)View Bookings\n5)Update a Stay\n6)Print Bill")
    option = int(input())

    if option == 1:
        id= input("Enter customer id: ")
        mobile_no= input("Enter mobile no. ")
        first_name = input("Enter First name ")
        last_name = input("Enter Last name ")
        age = input("Enter age ")

        sql = '''INSERT OR IGNORE INTO customer VALUES(?,?,?,?,?)'''
        values = (id, mobile_no, first_name, last_name, age,)

        cursor.execute(sql, values)
        connection.commit()

    elif option == 2:
        print("\n\t----------Welcome to room Booking Section---------- \n")

        print('''We have 3 types of Rooms :
                    1) Deluxe - Rs 2000/day
                    2) A/C Room - Rs 1000/day
                    3) Non-A/c Room - Rs 800/day ''')
        Choice = int(input())

        if Choice == 1:
            rate = 2000
        elif Choice == 2:
            rate = 1000
        elif Choice == 3:
            rate = 800
        else:
            print("Invalid Input....")

        room_id = input("Enter Room id: ")
        customer_id = input("Enter customer_id: ")
        Stay_days = int(input("Total Stay days: "))
        Rent = Stay_days * rate

        sql_1 = '''INSERT OR IGNORE INTO rooms VALUES(?,?,?,?,?)'''
        values_1 = (room_id, customer_id, rate, Stay_days, Rent)

        cursor.execute(sql_1, values_1)

        connection.commit()


    elif option==3:
        print("\t----------Welcome to Food Booking Section---------- \n")
        print("")
        id = int(input("Enter ID of Customer "))

        print('''
                Please select from the following Menu 
                1) Vegetarian Combo -----> 300 Rs.
                2) Non-Vegetarian Combo -----> 500 Rs.
                3) Vegetarian & Non-Vegetarian Combo -----> 750 Rs.''')
        c = int(input("Enter your choice: "))

        if c == 1:
            r = 300
        elif c == 2:
            r = 500
        elif c == 3:
            r = 750
        else:
            print("Invalid Input....")

        quantity = int(input("Please Enter the Quantity of Food: "))
        Bill = r * quantity

        sql_2 = '''INSERT OR IGNORE INTO food VALUES(?,?,?,?)'''
        values_2 = (id, r, quantity, Bill)

        cursor.execute(sql_2, values_2)

        connection.commit()


    elif option==4:
        print("Customer Entries are: ")
        cursor.execute("SELECT * FROM customer")

        results = cursor.fetchall()
        for result in results:
            print(result)
            print("\n\n")

        print("Booking Entries are: ")
        cursor.execute("SELECT * FROM rooms")
        results = cursor.fetchall()
        for result in results:
            print(result)
            print("\n\n")

        print("Total Food Orders are: ")
        cursor.execute("SELECT * FROM food")
        results = cursor.fetchall()
        for result in results:
            print(result)
            print("\n\n")
        connection.commit()

    elif option == 5:


        room_id = input("Enter the Room id: ")

        cursor.execute("SELECT Rate FROM rooms WHERE room_id =" + room_id)
        for row in cursor:
            rate = row[0]

        Stay_days = (input("Enter the new Room Stay Days: "))
        Rent = int(Stay_days) * rate

        temp = "UPDATE rooms SET Stay_days = " + Stay_days + ", Rent= "+ str(Rent) +" WHERE room_id = " + str(room_id)


        cursor.execute(temp)

    elif option == 6:
        Food_Bill = 0
        Room_Rent = 0
        customer_id = input("Enter Customer id: ")
        cursor.execute("SELECT Rent FROM rooms WHERE customer_id ="+customer_id )
        for row in cursor:
            Room_rent = row[0]

        cursor.execute("SELECT Bill FROM food WHERE id ="+customer_id )
        for row in cursor:
            Food_Bill = row[0]

        Final_Bill = Food_Bill + Room_rent
        print("Your Final Bill is: "+ str(Final_Bill))

    else:
        print("Invalid Option...")

    cont = input("\nDo you want to continue(y/n): ")

connection.commit()

db_df = pd.read_sql_query("SELECT * FROM customer", connection)
db_df.to_csv('customers.csv', index=False)

db_df = pd.read_sql_query("SELECT * FROM rooms", connection)
db_df.to_csv('rooms.csv', index=False)

db_df = pd.read_sql_query("SELECT * FROM food", connection)
db_df.to_csv('food.csv', index=False)

