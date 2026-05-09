package com.smithy.g.files.server.files.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Commit padre
 */

@Schema(name = "CommitParent", description = "Commit padre")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.5.0")
public class CommitParent {

  private String sha;

  private String url;

  public CommitParent() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CommitParent(String sha) {
    this.sha = sha;
  }

  public CommitParent sha(String sha) {
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

  public CommitParent url(String url) {
    this.url = url;
    return this;
  }

  /**
   * Get url
   * @return url
  */
  @Size(max = 500) 
  @Schema(name = "url", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("url")
  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommitParent commitParent = (CommitParent) o;
    return Objects.equals(this.sha, commitParent.sha) &&
        Objects.equals(this.url, commitParent.url);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sha, url);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CommitParent {\n");
    sb.append("    sha: ").append(toIndentedString(sha)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
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

