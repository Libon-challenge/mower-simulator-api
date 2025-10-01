### Mower Simulator API
Small REST API built in Java (Spring Boot) for the Libon technical exercise.<br>
It simulates the movement of automatic lawn mowers on a rectangular field.

### Tech Stack

Java 17<br>
Spring Boot 3.5.6<br>
Maven


### Problem

The field is defined by its top-right coordinates (bottom-left is always (0,0))
Each mower has a start position (x, y) and orientation (N, E, S, W)
Instructions:

G = turn left<br>
D = turn right<br>
A = move forward


Moves outside the field are ignored.
Mowers run one after the other (sequentially).


### Example
### Input
json{<br>
  "field": { "max_x": 5, "max_y": 5 },<br>
  "mowers": [<br>
    {<br>
      "id": "m1",<br>
      "start_position": { "x": 1, "y": 2 },<br>
      "orientation": "N",<br>
      "instructions": ["G", "A", "G", "A", "G", "A", "G", "A", "A"]<br>
    },<br>
    {<br>
      "id": "m2",<br>
      "start_position": { "x": 3, "y": 3 },<br>
      "orientation": "E",<br>
      "instructions": ["A", "A", "D", "A", "A", "D", "A", "D", "D", "A"]<br>
    }<br>
  ]<br>
}

### Output
json[<br>
  { "id": "m1", "position": { "x": 1, "y": 3 }, "orientation": "N" },<br>
  { "id": "m2", "position": { "x": 5, "y": 1 }, "orientation": "E" }<br>
]<br>

### Tests
A few unit tests were implemented to validate:

Correct execution of instructions (G, D, A)<br>
Sequential execution of multiple mowers<br>
Handling of invalid instructions (throws exception)

Note: Only basic coverage was implemented due to time constraints. More scenarios (invalid positions, orientations, edge cases, performance) could be added in the future.

### Out of Scope

Dockerfile<br>
Swagger/OpenAPI docs<br>
Collision detection
