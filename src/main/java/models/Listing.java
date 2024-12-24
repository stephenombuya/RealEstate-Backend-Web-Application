package com.realestate.app.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Represents a real estate listing.
 * This entity links a property with an agent, and includes details like the listing date, price, and whether it is featured.
 */
@Entity
@Table(name = "listings")
public class Listing {

    /**
     * The unique identifier for the listing.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The property being listed.
     */
    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    /**
     * The agent handling the listing.
     */
    @ManyToOne
    @JoinColumn(name = "agent_id", nullable = false)
    private Agent agent;

    /**
     * The date the listing was created.
     */
    @Column(nullable = false)
    private LocalDate listingDate;

    /**
     * The price at which the property is listed.
     */
    @Column(nullable = false)
    private BigDecimal listingPrice;

    /**
     * Whether the listing is featured or not.
     */
    private Boolean isFeatured;

    /**
     * Gets the unique identifier of the listing.
     * 
     * @return the unique identifier of the listing.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the listing.
     * 
     * @param id the unique identifier to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the property associated with this listing.
     * 
     * @return the property associated with the listing.
     */
    public Property getProperty() {
        return property;
    }

    /**
     * Sets the property associated with this listing.
     * 
     * @param property the property to associate with the listing.
     */
    public void setProperty(Property property) {
        this.property = property;
    }

    /**
     * Gets the agent handling the listing.
     * 
     * @return the agent handling the listing.
     */
    public Agent getAgent() {
        return agent;
    }

    /**
     * Sets the agent handling the listing.
     * 
     * @param agent the agent to handle the listing.
     */
    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    /**
     * Gets the listing date of the property.
     * 
     * @return the listing date.
     */
    public LocalDate getListingDate() {
        return listingDate;
    }

    /**
     * Sets the listing date of the property.
     * 
     * @param listingDate the listing date to set.
     */
    public void setListingDate(LocalDate listingDate) {
        this.listingDate = listingDate;
    }

    /**
     * Gets the listing price of the property.
     * 
     * @return the listing price.
     */
    public BigDecimal getListingPrice() {
        return listingPrice;
    }

    /**
     * Sets the listing price of the property.
     * 
     * @param listingPrice the listing price to set.
     */
    public void setListingPrice(BigDecimal listingPrice) {
        this.listingPrice = listingPrice;
    }

    /**
     * Gets whether the listing is featured.
     * 
     * @return true if the listing is featured, otherwise false.
     */
    public Boolean getIsFeatured() {
        return isFeatured;
    }

    /**
     * Sets whether the listing is featured.
     * 
     * @param isFeatured true to mark the listing as featured, false otherwise.
     */
    public void setIsFeatured(Boolean isFeatured) {
        this.isFeatured = isFeatured;
    }

    /**
     * Provides a string representation of the Listing object.
     * 
     * @return a string representation of the Listing object.
     */
    @Override
    public String toString() {
        return "Listing [id=" + id + ", property=" + property + ", agent=" + agent + ", listingDate=" + listingDate
                + ", listingPrice=" + listingPrice + ", isFeatured=" + isFeatured + "]";
    }
}
