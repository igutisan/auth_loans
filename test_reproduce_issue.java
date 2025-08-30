// Test script to reproduce the column mapping issue
// The problem: UserEntity maps field "names" to database column "name"
// but Spring R2DBC generates SQL using field name "names" instead of column name "name"

// From UserEntity.java:
// @Column(name = "name")
// private String names;

// This causes the SQL to be generated as:
// INSERT INTO user_entity (names, last_names, dob, phone, address, email, salary) VALUES (...)
// But the actual database column is "name", not "names"

// Solution: Fix the @Column annotation in UserEntity.java
public class TestReproduceIssue {
    // This script demonstrates the issue:
    // 1. UserEntity field "names" is mapped to database column "name"
    // 2. But Spring R2DBC generates SQL using field name "names"
    // 3. This causes "column 'names' does not exist" error
    
    // The fix is to correct the column mapping in UserEntity.java
}