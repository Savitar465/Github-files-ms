package com.smithy.g.files.server.files.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.smithy.g.files.server.files.model.CommitDTO;
import com.smithy.g.files.server.files.model.CommitFile;
import java.math.BigDecimal;
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
 * Comparacion entre branches (HU-20)
 */

@Schema(name = "CompareDTO", description = "Comparacion entre branches (HU-20)")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.5.0")
public class CompareDTO {

  @Valid
  private List<@Valid CommitDTO> commits = new ArrayList<>();

  private BigDecimal totalCommits;

  @Valid
  private List<@Valid CommitFile> files = new ArrayList<>();

  private BigDecimal aheadBy;

  private BigDecimal behindBy;

  public CompareDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CompareDTO(List<@Valid CommitDTO> commits, BigDecimal totalCommits, List<@Valid CommitFile> files, BigDecimal aheadBy, BigDecimal behindBy) {
    this.commits = commits;
    this.totalCommits = totalCommits;
    this.files = files;
    this.aheadBy = aheadBy;
    this.behindBy = behindBy;
  }

  public CompareDTO commits(List<@Valid CommitDTO> commits) {
    this.commits = commits;
    return this;
  }

  public CompareDTO addCommitsItem(CommitDTO commitsItem) {
    if (this.commits == null) {
      this.commits = new ArrayList<>();
    }
    this.commits.add(commitsItem);
    return this;
  }

  /**
   * Get commits
   * @return commits
  */
  @NotNull @Valid 
  @Schema(name = "commits", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("commits")
  public List<@Valid CommitDTO> getCommits() {
    return commits;
  }

  public void setCommits(List<@Valid CommitDTO> commits) {
    this.commits = commits;
  }

  public CompareDTO totalCommits(BigDecimal totalCommits) {
    this.totalCommits = totalCommits;
    return this;
  }

  /**
   * Get totalCommits
   * @return totalCommits
  */
  @NotNull @Valid 
  @Schema(name = "totalCommits", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("totalCommits")
  public BigDecimal getTotalCommits() {
    return totalCommits;
  }

  public void setTotalCommits(BigDecimal totalCommits) {
    this.totalCommits = totalCommits;
  }

  public CompareDTO files(List<@Valid CommitFile> files) {
    this.files = files;
    return this;
  }

  public CompareDTO addFilesItem(CommitFile filesItem) {
    if (this.files == null) {
      this.files = new ArrayList<>();
    }
    this.files.add(filesItem);
    return this;
  }

  /**
   * Get files
   * @return files
  */
  @NotNull @Valid 
  @Schema(name = "files", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("files")
  public List<@Valid CommitFile> getFiles() {
    return files;
  }

  public void setFiles(List<@Valid CommitFile> files) {
    this.files = files;
  }

  public CompareDTO aheadBy(BigDecimal aheadBy) {
    this.aheadBy = aheadBy;
    return this;
  }

  /**
   * Get aheadBy
   * @return aheadBy
  */
  @NotNull @Valid 
  @Schema(name = "aheadBy", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("aheadBy")
  public BigDecimal getAheadBy() {
    return aheadBy;
  }

  public void setAheadBy(BigDecimal aheadBy) {
    this.aheadBy = aheadBy;
  }

  public CompareDTO behindBy(BigDecimal behindBy) {
    this.behindBy = behindBy;
    return this;
  }

  /**
   * Get behindBy
   * @return behindBy
  */
  @NotNull @Valid 
  @Schema(name = "behindBy", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("behindBy")
  public BigDecimal getBehindBy() {
    return behindBy;
  }

  public void setBehindBy(BigDecimal behindBy) {
    this.behindBy = behindBy;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CompareDTO compareDTO = (CompareDTO) o;
    return Objects.equals(this.commits, compareDTO.commits) &&
        Objects.equals(this.totalCommits, compareDTO.totalCommits) &&
        Objects.equals(this.files, compareDTO.files) &&
        Objects.equals(this.aheadBy, compareDTO.aheadBy) &&
        Objects.equals(this.behindBy, compareDTO.behindBy);
  }

  @Override
  public int hashCode() {
    return Objects.hash(commits, totalCommits, files, aheadBy, behindBy);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CompareDTO {\n");
    sb.append("    commits: ").append(toIndentedString(commits)).append("\n");
    sb.append("    totalCommits: ").append(toIndentedString(totalCommits)).append("\n");
    sb.append("    files: ").append(toIndentedString(files)).append("\n");
    sb.append("    aheadBy: ").append(toIndentedString(aheadBy)).append("\n");
    sb.append("    behindBy: ").append(toIndentedString(behindBy)).append("\n");
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

