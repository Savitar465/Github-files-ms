package com.smithy.g.files.server.files.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.smithy.g.files.server.files.model.CommitParent;
import com.smithy.g.files.server.files.model.CommitSignature;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Commit completo
 */

@Schema(name = "CommitDTO", description = "Commit completo")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.5.0")
public class CommitDTO {

  private String sha;

  private String message;

  private CommitSignature author;

  private CommitSignature committer;

  private String htmlUrl;

  @Valid
  private List<@Valid CommitParent> parents = new ArrayList<>();

  public CommitDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CommitDTO(String sha, String message, CommitSignature author, CommitSignature committer) {
    this.sha = sha;
    this.message = message;
    this.author = author;
    this.committer = committer;
  }

  public CommitDTO sha(String sha) {
    this.sha = sha;
    return this;
  }

  /**
   * Get sha
   * @return sha
  */
  @NotNull 
  @Schema(name = "sha", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("sha")
  public String getSha() {
    return sha;
  }

  public void setSha(String sha) {
    this.sha = sha;
  }

  public CommitDTO message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Get message
   * @return message
  */
  @NotNull 
  @Schema(name = "message", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("message")
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public CommitDTO author(CommitSignature author) {
    this.author = author;
    return this;
  }

  /**
   * Get author
   * @return author
  */
  @NotNull @Valid 
  @Schema(name = "author", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("author")
  public CommitSignature getAuthor() {
    return author;
  }

  public void setAuthor(CommitSignature author) {
    this.author = author;
  }

  public CommitDTO committer(CommitSignature committer) {
    this.committer = committer;
    return this;
  }

  /**
   * Get committer
   * @return committer
  */
  @NotNull @Valid 
  @Schema(name = "committer", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("committer")
  public CommitSignature getCommitter() {
    return committer;
  }

  public void setCommitter(CommitSignature committer) {
    this.committer = committer;
  }

  public CommitDTO htmlUrl(String htmlUrl) {
    this.htmlUrl = htmlUrl;
    return this;
  }

  /**
   * Get htmlUrl
   * @return htmlUrl
  */
  @Size(max = 500) 
  @Schema(name = "htmlUrl", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("htmlUrl")
  public String getHtmlUrl() {
    return htmlUrl;
  }

  public void setHtmlUrl(String htmlUrl) {
    this.htmlUrl = htmlUrl;
  }

  public CommitDTO parents(List<@Valid CommitParent> parents) {
    this.parents = parents;
    return this;
  }

  public CommitDTO addParentsItem(CommitParent parentsItem) {
    if (this.parents == null) {
      this.parents = new ArrayList<>();
    }
    this.parents.add(parentsItem);
    return this;
  }

  /**
   * Get parents
   * @return parents
  */
  @Valid 
  @Schema(name = "parents", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("parents")
  public List<@Valid CommitParent> getParents() {
    return parents;
  }

  public void setParents(List<@Valid CommitParent> parents) {
    this.parents = parents;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommitDTO commitDTO = (CommitDTO) o;
    return Objects.equals(this.sha, commitDTO.sha) &&
        Objects.equals(this.message, commitDTO.message) &&
        Objects.equals(this.author, commitDTO.author) &&
        Objects.equals(this.committer, commitDTO.committer) &&
        Objects.equals(this.htmlUrl, commitDTO.htmlUrl) &&
        Objects.equals(this.parents, commitDTO.parents);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sha, message, author, committer, htmlUrl, parents);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CommitDTO {\n");
    sb.append("    sha: ").append(toIndentedString(sha)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    author: ").append(toIndentedString(author)).append("\n");
    sb.append("    committer: ").append(toIndentedString(committer)).append("\n");
    sb.append("    htmlUrl: ").append(toIndentedString(htmlUrl)).append("\n");
    sb.append("    parents: ").append(toIndentedString(parents)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

