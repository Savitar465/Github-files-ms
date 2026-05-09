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
 * Contenido de archivo (respuesta GET para archivos)
 */

@Schema(name = "FileContentDTO", description = "Contenido de archivo (respuesta GET para archivos)")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.5.0")
public class FileContentDTO {

  private String name;

  private String path;

  private String sha;

  private GitObjectType type;

  private BigDecimal size;

  private String encoding;

  private String content;

  private String downloadUrl;

  private String htmlUrl;

  private String lastCommitSha;

  public FileContentDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public FileContentDTO(String name, String path, String sha, GitObjectType type, String downloadUrl) {
    this.name = name;
    this.path = path;
    this.sha = sha;
    this.type = type;
    this.downloadUrl = downloadUrl;
  }

  public FileContentDTO name(String name) {
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

  public FileContentDTO path(String path) {
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

  public FileContentDTO sha(String sha) {
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

  public FileContentDTO type(GitObjectType type) {
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

  public FileContentDTO size(BigDecimal size) {
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

  public FileContentDTO encoding(String encoding) {
    this.encoding = encoding;
    return this;
  }

  /**
   * \"base64\" para archivos con contenido
   * @return encoding
  */
  
  @Schema(name = "encoding", description = "\"base64\" para archivos con contenido", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("encoding")
  public String getEncoding() {
    return encoding;
  }

  public void setEncoding(String encoding) {
    this.encoding = encoding;
  }

  public FileContentDTO content(String content) {
    this.content = content;
    return this;
  }

  /**
   * Contenido del archivo en base64
   * @return content
  */
  
  @Schema(name = "content", description = "Contenido del archivo en base64", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("content")
  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public FileContentDTO downloadUrl(String downloadUrl) {
    this.downloadUrl = downloadUrl;
    return this;
  }

  /**
   * Get downloadUrl
   * @return downloadUrl
  */
  @NotNull @Size(max = 500) 
  @Schema(name = "downloadUrl", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("downloadUrl")
  public String getDownloadUrl() {
    return downloadUrl;
  }

  public void setDownloadUrl(String downloadUrl) {
    this.downloadUrl = downloadUrl;
  }

  public FileContentDTO htmlUrl(String htmlUrl) {
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

  public FileContentDTO lastCommitSha(String lastCommitSha) {
    this.lastCommitSha = lastCommitSha;
    return this;
  }

  /**
   * SHA del ultimo commit que modifico el archivo
   * @return lastCommitSha
  */
  
  @Schema(name = "lastCommitSha", description = "SHA del ultimo commit que modifico el archivo", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("lastCommitSha")
  public String getLastCommitSha() {
    return lastCommitSha;
  }

  public void setLastCommitSha(String lastCommitSha) {
    this.lastCommitSha = lastCommitSha;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FileContentDTO fileContentDTO = (FileContentDTO) o;
    return Objects.equals(this.name, fileContentDTO.name) &&
        Objects.equals(this.path, fileContentDTO.path) &&
        Objects.equals(this.sha, fileContentDTO.sha) &&
        Objects.equals(this.type, fileContentDTO.type) &&
        Objects.equals(this.size, fileContentDTO.size) &&
        Objects.equals(this.encoding, fileContentDTO.encoding) &&
        Objects.equals(this.content, fileContentDTO.content) &&
        Objects.equals(this.downloadUrl, fileContentDTO.downloadUrl) &&
        Objects.equals(this.htmlUrl, fileContentDTO.htmlUrl) &&
        Objects.equals(this.lastCommitSha, fileContentDTO.lastCommitSha);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, path, sha, type, size, encoding, content, downloadUrl, htmlUrl, lastCommitSha);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FileContentDTO {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    path: ").append(toIndentedString(path)).append("\n");
    sb.append("    sha: ").append(toIndentedString(sha)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    size: ").append(toIndentedString(size)).append("\n");
    sb.append("    encoding: ").append(toIndentedString(encoding)).append("\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("    downloadUrl: ").append(toIndentedString(downloadUrl)).append("\n");
    sb.append("    htmlUrl: ").append(toIndentedString(htmlUrl)).append("\n");
    sb.append("    lastCommitSha: ").append(toIndentedString(lastCommitSha)).append("\n");
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

