## Week 8 Lab Activity

**Name:** John Carlo Valdez
**ID:** 2511004
**Course/Year:** BSIT-2

## Task 1 - Create Repository

What I did: Created the Pet entity with id, microchipNumber (unique and not null), name, species, and breed. Then created PetRepository extending JpaRepository<Pet, Long>.

How it works: Spring Data JPA automatically gives me built-in methods like findAll(), save(), and deleteById() - I don't write any SQL myself. When I called findAll(), it returned the list of existing pet records from the database successfully.✅

## Task 2 - Full CRUD Operations

What I did: Built PetService with all 5 operations and PetController to expose them as REST endpoints.

Results:

- POST /api/pets >> Creates a new pet, returns 201 Created✅
- GET /api/pets >> Returns all pets, returns 200 OK ✅
- GET /api/pets/{id} >> Returns one pet by ID; throws 404 Not Found if it doesn't exist✅
- PUT /api/pets/{id} >> Updates existing pet info, returns 200 OK✅
- DELETE /api/pets/{id} >> Deletes pet by ID, returns 204 No Content✅

All 5 operations work correctly and data changes are saved in the database.✅

## Task 3 - Custom Queries

What I did: Added 3 methods in PetRepository: findBySpeciesIgnoreCase, findByNameContainingIgnoreCase, and a JPQL query for findAllSpecies. Connected them through the service and controller.

Results:

- GET /api/pets/species?name=Dog >> Returns only pets where species matches, case doesn't matter✅
- GET /api/pets/search?key=Bella >>  Returns pets with "Bella" anywhere in their name✅
- GET /api/pets/species-list >> Returns only unique species names, no duplicates✅

Spring automatically generates the queries just from the method names - very convenient!✅

## Task 4 - @Transactional & Rollback

What I did: Added batchRegister method with @Transactional. Sent a list of 2 pets where the second one had a duplicate microchipNumber.
What happened: The first pet started saving, but when the second one failed due to the unique constraint, @Transactional automatically rolled back EVERYTHING. When I checked the database afterward, zero rows were saved - not even the first one. The SQL log showed the INSERT happened but then was undone.

Conclusion: @Transactional ensures atomicity - either ALL saves succeed, or NOTHING is saved. Prevents partial/incomplete data.✅

## Task 5 - Debugging the Broken Entity

What I found - 3 bugs:

1.❌Missing  @GeneratedValue  on id >> Hibernate couldn't auto-generate primary keys
2.❌Missing the  species  field entirely >> Column didn't exist in the table
3.❌Setting  microchipNumber  to null >> Violates the NOT NULL constraint

Stack trace: Showed SQL error - NULL value in non-null column
SQL log: Showed the INSERT statement failed and wasn't executed
What fixed it:

1.✅Added  @GeneratedValue(strategy = GenerationType.IDENTITY)  to id
2.✅Added  private String species;  field to the Pet class
3.✅Used a valid unique microchip number instead of null

After all 3 fixes, the pet saved successfully with no errors.✅
