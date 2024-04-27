# Hogwarts 5

## Test af prefect funktioner

#### POST /prefects - modtager en student (eller et student-id) og udnævner vedkommende til prefect
Kan testes ved at sende et POST request til `/prefects/:id` med et student id. Studenten med det givne id skal være 5. års eller højere, der må ikke allerede være 2 prefects i samme house og studenten må ikke allerede være prefects i det house, som student er i, og der må ikke være en prefect af samme køn.

### DELETE /prefects/:id - fratager den pågældende student rollen som prefect.
Kan testes ved at sende et DELETE request til `/prefects/{id}`. Studenten med det givne id skal være en prefect.

### GET /prefects - returnerer en liste over alle prefects i alle houses
Kan testes ved at sende et GET request til ´/prefects´.

### GET /prefects/:id - returnerer en prefect (ud fra student-id) hvis den pågældende student er prefect
Kan testes ved at sende et GET request til ´/prefects/{id}´. Id skal være id'et på en student der er prefect.

### GET /prefects/house/{house} - returnerer en liste over alle prefects i det house
Kan testes ved at sende et GET request til ´/prefects/house/{house}´. House skal være navnet på et af de 4 houses.

### /students skal ligeledes have en PATCH request for at tilføje/fjerne prefect udnævnelsen - men bruge samme regler som /prefects
Kan testes ved at sende et PATCH request til `/students/{id}` med et student JSON objekt, hvor isPrefect indgår. Studenten med det givne id skal være 5. års eller højere, der må ikke allerede være 2 prefects i samme house og studenten må ikke allerede være prefects i det house, som student er i, og der må ikke være en prefect af samme køn.

```json
{
    "isPrefect": true
}
```