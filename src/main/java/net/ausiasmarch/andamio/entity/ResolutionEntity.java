/*
 * Copyright (c) 2021
 *
 * by Rafael Angel Aznar Aparici (rafaaznar at gmail dot com) & 2021 DAW students
 *
 * WILDCART: Free Open Source Shopping Site
 *
 * Sources at:                https://github.com/rafaelaznar/wildCartSBServer2021
 * Database at:               https://github.com/rafaelaznar/wildCartSBServer2021
 * POSTMAN API at:            https://github.com/rafaelaznar/wildCartSBServer2021
 * Client at:                 https://github.com/rafaelaznar/wildCartAngularClient2021
 *
 * WILDCART is distributed under the MIT License (MIT)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.ausiasmarch.andamio.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.ManyToOne;

@Entity
@Table(name = "resolution")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ResolutionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_issue")          
    private IssueEntity issue;
    
    private String observations;
    
    private long integration_turn;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime integration_datetime;
    
    private String pullrequest_url;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_developer")          
    private DeveloperEntity developer;
    
    private int value;
    
    @OneToMany(mappedBy = "resolution", fetch = FetchType.LAZY)
    private final List<HelpEntity> helps;


    public ResolutionEntity(List<HelpEntity> helps) {
        this.helps = helps;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IssueEntity getIssue() {
        return issue;
    }

    public void setIssue(IssueEntity issue) {
        this.issue = issue;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public long getIntegration_turn() {
        return integration_turn;
    }

    public void setIntegration_turn(long integration_turn) {
        this.integration_turn = integration_turn;
    }

    public LocalDateTime getIntegration_datetime() {
        return integration_datetime;
    }

    public void setIntegration_datetime(LocalDateTime integration_datetime) {
        this.integration_datetime = integration_datetime;
    }

    public String getPullrequest_url() {
        return pullrequest_url;
    }

    public void setPullrequest_url(String pullrequest_url) {
        this.pullrequest_url = pullrequest_url;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public List<HelpEntity> getHelps() {
        return helps;
    }
    
    public DeveloperEntity getDeveloper() {
        return developer;
    }

    public void setDeveloper(DeveloperEntity developer) {
        this.developer = developer;
    }
    
    
    
    }