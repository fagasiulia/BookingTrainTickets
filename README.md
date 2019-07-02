# BookingTrainTickets
This program will let the user book train tickets, by reading routes info from a database

We have MersulTrenurilor.db. This database contains five tables:
1. Rute(ID, RUTA)
2. Statii(ID_RUTA, ID_STATIE, STATIE, KM)
3. SosiriPlecari(ID_TREN, ID_STATIE, SOSIRE, PLECARE)
4. Trenuri(ID_TREN)
5. Locuri(ID_TREN, NUMAR_LOCURI, BOOKED)

Using this program the user can do the following:
1. Select the departure city, the arrival one and a date. The program will search for a route in the database, if a match is found, it will return the departure hour options and the price. The price of the ticket will be calculated based on kilometers. 
2. If the user agrees to continue, the program will ask for the user's name and it will print the ticket details to a file. The file will contain the following information : name of the user, route, train number, departure - arrival hour and date, seat number.

Note:
The user should not be able to book a seat if the seat is already taken or if there are no seats available.

