/**
 * Represents an owner
 *
 * @author law37
 * @version 1.1 (23th March 2018)
 */
public class Owner {
    private String name;
    private String phone;

    /**
     * Owner constructor
     * @param n name
     * @param p phone number
     */
    public Owner(String n, String p) {
        name = n;
        phone = p;
    }

    /**
     * get name of owner
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * get phone number
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * equals method
     * @param obj object
     * @return true
     */
    @Override
    public boolean equals(Object obj) { // Robust version generated by IDE
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Owner other = (Owner) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (phone == null) {
            if (other.phone != null)
                return false;
        } else if (!phone.equals(other.phone))
            return false;
        return true;
    }

    /**
     *  return name and string
     * @return name and phone
     */
    @Override
    public String toString() {
        return name + " " + phone;
    }

}