package gateway.service.constant;

import java.util.*;

final public class PrivateURL {
    private static final Map<String, List<String>> pathRoleMap = new HashMap<>();
    private static final List<String> roleHierarchy = Arrays.asList("ROLE_GUEST", "ROLE_USER", "ROLE_ADMIN","ROLE_OWNER");

    static {
        // Adding paths and associated roles
        addPathWithRoles("/slack-bot/leaderboard", Arrays.asList("ROLE_ADMIN", "ROLE_OWNER"));
        addPathWithRoles("/user-activity", Collections.singletonList("ROLE_USER"));
        addPathWithRoles("/guest", Collections.singletonList("ROLE_GUEST"));
    }

    // Helper method to add a path with roles to the map
    private static void addPathWithRoles(String path, List<String> roles) {
        pathRoleMap.put(path, roles);
    }

    // Method to compare roles with the map for the given path, considering role hierarchy
    public static boolean areAllRolesPresent(String path, List<String> rolesToCheck) {
        // Get the roles associated with the given path
        List<String> rolesForPath = pathRoleMap.get(path);

        // If the path doesn't exist in the map, return false
        if (rolesForPath == null) {
            return false;
        }

        // Check if any of the roles in rolesToCheck are present or higher in privilege
        for (String role : rolesToCheck) {
            if (isRolePresentOrHigher(role, rolesForPath)) {
                return true;  // Grant access if any role matches or has higher privilege
            }
        }

        return false;
    }

    // Helper method to check if a role or any higher privilege role is present
    private static boolean isRolePresentOrHigher(String roleToCheck, List<String> rolesForPath) {
        int roleIndex = roleHierarchy.indexOf(roleToCheck);

        // Ensure roleIndex is found in roleHierarchy
        if (roleIndex == -1) {
            return false;
        }

        // Check if the required role is of equal or lower privilege
        for (String requiredRole : rolesForPath) {
            int requiredRoleIndex = roleHierarchy.indexOf(requiredRole);

            // If the required role is of equal or higher privilege than the roleToCheck, grant access
            if (requiredRoleIndex <= roleIndex) {
                return true;
            }
        }

        return false;
    }
/*
    // Test method for demonstration
    public static void main(String[] args) {
        // Test with USER role for a path requiring ADMIN role
        boolean result1 = areAllRolesPresent("/slack-bot/leaderboard", Arrays.asList("USER"));
        System.out.println("Result1: " + result1);  // Should print: false (USER cannot access ADMIN path)

        // Test with ADMIN role for a path with USER role (ADMIN has higher privilege)
        boolean result2 = areAllRolesPresent("/user-activity", Arrays.asList("ADMIN"));
        System.out.println("Result2: " + result2);  // Should print: true (ADMIN can access USER path)

        // Test with a missing role
        boolean result3 = areAllRolesPresent("/guest", Arrays.asList("USER"));
        System.out.println("Result3: " + result3);  // Should print: false (USER cannot access GUEST path)

        // Test with ADMIN role for a path that requires ADMIN role
        boolean result4 = areAllRolesPresent("/slack-bot/leaderboard", Arrays.asList("ADMIN"));
        System.out.println("Result4: " + result4);  // Should print: true (ADMIN has access)
    }*/
}
