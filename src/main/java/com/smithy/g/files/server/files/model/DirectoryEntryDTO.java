package com.smithy.g.files.server.files.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.smithy.g.files.server.files.model.GitObjectType;
import java.math.BigDecimal;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Entrada de directorio (para listar contenido de carpetas)
 */

@Schema(name = "DirectoryEntryDTO", description = "Entrada de directorio (para listar contenido de carpetas)")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.5.0")
public class DirectoryEntryDTO {

  private String name;

  private String path;

  private String sha;

  private GitObjectType type;

  private BigDecimal size;

  private String downloadUrl;

  public DirectoryEntryDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public DirectoryEntryDTO(String name, String path, String sha, GitObjectType type) {
    this.name = name;
    this.path = path;
    this.sha = sha;
    this.type = type;
  }

  public DirectoryEntryDTO name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  */
  @NotNull 
  @Schema(name = "name", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public DirectoryEntryDTO path(String path) {
    this.path = path;
    return this;
  }

  /**
   * Get path
   * @return path
  */
  @NotNull 
  @Schema(name = "path", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("path")
  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public DirectoryEntryDTO sha(String sha) {
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

  public DirectoryEntryDTO type(GitObjectType type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
  */
  @NotNull @Valid 
  @Schema(name = "type", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("type")
  public GitObjectType getType() {
    return type;
  }

  public void setType(GitObjectType type) {
    this.type = type;
  }

  public DirectoryEntryDTO size(BigDecimal size) {
    this.size = size;
    return this;
  }

  /**
   * Get size
   * @return size
  */
  @Valid 
  @Schema(name = "size", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("size")
  public BigDecimal getSize() {
    return size;
  }

  public void setSize(BigDecimal size) {
    this.size = size;
  }

  public DirectoryEntryDTO downloadUrl(String downloadUrl) {
    this.downloadUrl = downloadUrl;
    return this;
  }

  /**
   * Get downloadUrl
   * @return downloadUrl
  */
  @Size(max = 500) 
  @Schema(name = "downloadUrl", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("downloadUrl")
  public String getDownloadUrl() {
    return downloadUrl;
  }

  public void setDownloadUrl(String downloadUrl) {
    this.downloadUrl = downloadUrl;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DirectoryEntryDTO directoryEntryDTO = (DirectoryEntryDTO) o;
    return Objects.equals(this.name, directoryEntryDTO.name) &&
        Objects.equals(this.path, directoryEntryDTO.path) &&
        Objects.equals(this.sha, directoryEntryDTO.sha) &&
        Objects.equals(this.type, directoryEntryDTO.type) &&
        Objects.equals(this.size, directoryEntryDTO.size) &&
        Objects.equals(this.downloadUrl, directoryEntryDTO.downloadUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, path, sha, type, size, downloadUrl);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DirectoryEntryDTO {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    path: ").append(toIndentedString(path)).append("\n");
    sb.append("    sha: ").append(toIndentedString(sha)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    size: ").append(toIndentedString(size)).append("\n");
    sb.append("    downloadUrl: ").append(toIndentedString(downloadUrl)).append("\n");
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

