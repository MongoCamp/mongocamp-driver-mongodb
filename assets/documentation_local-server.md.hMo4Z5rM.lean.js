import{_ as s,c as a,a2 as t,o as l}from"./chunks/framework.DPuwY6B9.js";const d=JSON.parse('{"title":"LocalServer","description":"","frontmatter":{},"headers":[],"relativePath":"documentation/local-server.md","filePath":"documentation/local-server.md","lastUpdated":1730840042000}'),n={name:"documentation/local-server.md"};function e(h,i,k,p,o,r){return l(),a("div",null,i[0]||(i[0]=[t(`<h1 id="localserver" tabindex="-1">LocalServer <a class="header-anchor" href="#localserver" aria-label="Permalink to &quot;LocalServer&quot;">​</a></h1><p>mongocamp supports LocalServer by <a href="https://github.com/bwaldvogel/mongo-java-server" target="_blank" rel="noreferrer">mongo-java-server</a> (since 2.0.1).</p><p>Useful for tests and small offline Apps. Dependency to mongo-java-server is marked as provided, you have to import the dependency by yourself.</p><div class="language-scala vp-adaptive-theme"><button title="Copy Code" class="copy"></button><span class="lang">scala</span><pre class="shiki shiki-themes github-light github-dark vp-code" tabindex="0"><code><span class="line"><span style="--shiki-light:#6A737D;--shiki-dark:#6A737D;">// Base dependency</span></span>
<span class="line"><span style="--shiki-light:#24292E;--shiki-dark:#E1E4E8;">libraryDependencies </span><span style="--shiki-light:#D73A49;--shiki-dark:#F97583;">+=</span><span style="--shiki-light:#032F62;--shiki-dark:#9ECBFF;"> &quot;de.bwaldvogel&quot;</span><span style="--shiki-light:#D73A49;--shiki-dark:#F97583;"> %</span><span style="--shiki-light:#032F62;--shiki-dark:#9ECBFF;"> &quot;mongo-java-server&quot;</span><span style="--shiki-light:#D73A49;--shiki-dark:#F97583;"> %</span><span style="--shiki-light:#032F62;--shiki-dark:#9ECBFF;"> &quot;1.28.0&quot;</span></span>
<span class="line"></span>
<span class="line"><span style="--shiki-light:#6A737D;--shiki-dark:#6A737D;">// H2 Backend</span></span>
<span class="line"><span style="--shiki-light:#24292E;--shiki-dark:#E1E4E8;">libraryDependencies </span><span style="--shiki-light:#D73A49;--shiki-dark:#F97583;">+=</span><span style="--shiki-light:#032F62;--shiki-dark:#9ECBFF;"> &quot;de.bwaldvogel&quot;</span><span style="--shiki-light:#D73A49;--shiki-dark:#F97583;"> %</span><span style="--shiki-light:#032F62;--shiki-dark:#9ECBFF;"> &quot;mongo-java-server-h2-backend&quot;</span><span style="--shiki-light:#D73A49;--shiki-dark:#F97583;"> %</span><span style="--shiki-light:#032F62;--shiki-dark:#9ECBFF;"> &quot;1.28.0&quot;</span></span></code></pre></div><h2 id="localserver-modes" tabindex="-1">LocalServer Modes <a class="header-anchor" href="#localserver-modes" aria-label="Permalink to &quot;LocalServer Modes&quot;">​</a></h2><ul><li>InMemory</li><li>H2 InMemory</li><li>H2 file based</li></ul><h2 id="setup" tabindex="-1">Setup <a class="header-anchor" href="#setup" aria-label="Permalink to &quot;Setup&quot;">​</a></h2><p>LocalServer is done by ServerConfig. For InMemory tests no special stup is needed.</p><div class="language-scala vp-adaptive-theme"><button title="Copy Code" class="copy"></button><span class="lang">scala</span><pre class="shiki shiki-themes github-light github-dark vp-code" tabindex="0"><code><span class="line"><span style="--shiki-light:#D73A49;--shiki-dark:#F97583;">val</span><span style="--shiki-light:#E36209;--shiki-dark:#FFAB70;"> LocalTestServer</span><span style="--shiki-light:#D73A49;--shiki-dark:#F97583;"> =</span><span style="--shiki-light:#6F42C1;--shiki-dark:#B392F0;"> LocalServer</span><span style="--shiki-light:#24292E;--shiki-dark:#E1E4E8;">()</span></span></code></pre></div><h3 id="setup-with-application-config" tabindex="-1">Setup with application config <a class="header-anchor" href="#setup-with-application-config" aria-label="Permalink to &quot;Setup with application config&quot;">​</a></h3><div class="language-json vp-adaptive-theme"><button title="Copy Code" class="copy"></button><span class="lang">json</span><pre class="shiki shiki-themes github-light github-dark vp-code" tabindex="0"><code><span class="line"><span style="--shiki-light:#24292E;--shiki-dark:#E1E4E8;">unit.test.local.mongo.server {</span></span>
<span class="line"><span style="--shiki-light:#B31D28;--shiki-light-font-style:italic;--shiki-dark:#FDAEB7;--shiki-dark-font-style:italic;">  host</span><span style="--shiki-light:#B31D28;--shiki-light-font-style:italic;--shiki-dark:#FDAEB7;--shiki-dark-font-style:italic;"> =</span><span style="--shiki-light:#005CC5;--shiki-dark:#79B8FF;"> &quot;localhost&quot;</span></span>
<span class="line"><span style="--shiki-light:#B31D28;--shiki-light-font-style:italic;--shiki-dark:#FDAEB7;--shiki-dark-font-style:italic;">  port</span><span style="--shiki-light:#B31D28;--shiki-light-font-style:italic;--shiki-dark:#FDAEB7;--shiki-dark-font-style:italic;"> =</span><span style="--shiki-light:#B31D28;--shiki-light-font-style:italic;--shiki-dark:#FDAEB7;--shiki-dark-font-style:italic;"> 28028</span></span>
<span class="line"><span style="--shiki-light:#B31D28;--shiki-light-font-style:italic;--shiki-dark:#FDAEB7;--shiki-dark-font-style:italic;">  serverName</span><span style="--shiki-light:#B31D28;--shiki-light-font-style:italic;--shiki-dark:#FDAEB7;--shiki-dark-font-style:italic;"> =</span><span style="--shiki-light:#005CC5;--shiki-dark:#79B8FF;"> &quot;local-unit-test-server&quot;</span></span>
<span class="line"><span style="--shiki-light:#B31D28;--shiki-light-font-style:italic;--shiki-dark:#FDAEB7;--shiki-dark-font-style:italic;">  backend</span><span style="--shiki-light:#B31D28;--shiki-light-font-style:italic;--shiki-dark:#FDAEB7;--shiki-dark-font-style:italic;"> =</span><span style="--shiki-light:#005CC5;--shiki-dark:#79B8FF;"> &quot;h2&quot;</span></span>
<span class="line"><span style="--shiki-light:#B31D28;--shiki-light-font-style:italic;--shiki-dark:#FDAEB7;--shiki-dark-font-style:italic;">  h2</span><span style="--shiki-light:#B31D28;--shiki-light-font-style:italic;--shiki-dark:#FDAEB7;--shiki-dark-font-style:italic;"> {</span></span>
<span class="line"><span style="--shiki-light:#B31D28;--shiki-light-font-style:italic;--shiki-dark:#FDAEB7;--shiki-dark-font-style:italic;">    inMemory</span><span style="--shiki-light:#B31D28;--shiki-light-font-style:italic;--shiki-dark:#FDAEB7;--shiki-dark-font-style:italic;"> =</span><span style="--shiki-light:#B31D28;--shiki-light-font-style:italic;--shiki-dark:#FDAEB7;--shiki-dark-font-style:italic;"> false</span></span>
<span class="line"><span style="--shiki-light:#B31D28;--shiki-light-font-style:italic;--shiki-dark:#FDAEB7;--shiki-dark-font-style:italic;">    path</span><span style="--shiki-light:#B31D28;--shiki-light-font-style:italic;--shiki-dark:#FDAEB7;--shiki-dark-font-style:italic;"> =</span><span style="--shiki-light:#005CC5;--shiki-dark:#79B8FF;"> &quot;&quot;</span><span style="--shiki-light:#6A737D;--shiki-dark:#6A737D;"> // without path set a random temp file is created</span></span>
<span class="line"><span style="--shiki-light:#24292E;--shiki-dark:#E1E4E8;">  }</span></span>
<span class="line"><span style="--shiki-light:#24292E;--shiki-dark:#E1E4E8;">}</span></span>
<span class="line"></span>
<span class="line"><span style="--shiki-light:#24292E;--shiki-dark:#E1E4E8;">unit.test.mongo.local {</span></span>
<span class="line"><span style="--shiki-light:#B31D28;--shiki-light-font-style:italic;--shiki-dark:#FDAEB7;--shiki-dark-font-style:italic;">  database</span><span style="--shiki-light:#B31D28;--shiki-light-font-style:italic;--shiki-dark:#FDAEB7;--shiki-dark-font-style:italic;"> =</span><span style="--shiki-light:#005CC5;--shiki-dark:#79B8FF;"> &quot;mongocamp-unit-test&quot;</span></span>
<span class="line"><span style="--shiki-light:#B31D28;--shiki-light-font-style:italic;--shiki-dark:#FDAEB7;--shiki-dark-font-style:italic;">  host</span><span style="--shiki-light:#B31D28;--shiki-light-font-style:italic;--shiki-dark:#FDAEB7;--shiki-dark-font-style:italic;"> =</span><span style="--shiki-light:#005CC5;--shiki-dark:#79B8FF;"> &quot;localhost&quot;</span></span>
<span class="line"><span style="--shiki-light:#B31D28;--shiki-light-font-style:italic;--shiki-dark:#FDAEB7;--shiki-dark-font-style:italic;">  port</span><span style="--shiki-light:#B31D28;--shiki-light-font-style:italic;--shiki-dark:#FDAEB7;--shiki-dark-font-style:italic;"> =</span><span style="--shiki-light:#B31D28;--shiki-light-font-style:italic;--shiki-dark:#FDAEB7;--shiki-dark-font-style:italic;"> 28028</span></span>
<span class="line"><span style="--shiki-light:#B31D28;--shiki-light-font-style:italic;--shiki-dark:#FDAEB7;--shiki-dark-font-style:italic;">  userName</span><span style="--shiki-light:#B31D28;--shiki-light-font-style:italic;--shiki-dark:#FDAEB7;--shiki-dark-font-style:italic;"> =</span><span style="--shiki-light:#005CC5;--shiki-dark:#79B8FF;"> &quot;testy&quot;</span></span>
<span class="line"><span style="--shiki-light:#B31D28;--shiki-light-font-style:italic;--shiki-dark:#FDAEB7;--shiki-dark-font-style:italic;">  password</span><span style="--shiki-light:#B31D28;--shiki-light-font-style:italic;--shiki-dark:#FDAEB7;--shiki-dark-font-style:italic;"> =</span><span style="--shiki-light:#005CC5;--shiki-dark:#79B8FF;"> &quot;&quot;</span></span>
<span class="line"><span style="--shiki-light:#B31D28;--shiki-light-font-style:italic;--shiki-dark:#FDAEB7;--shiki-dark-font-style:italic;">  applicationName</span><span style="--shiki-light:#B31D28;--shiki-light-font-style:italic;--shiki-dark:#FDAEB7;--shiki-dark-font-style:italic;"> =</span><span style="--shiki-light:#005CC5;--shiki-dark:#79B8FF;"> &quot;mongocamp-config-test&quot;</span></span>
<span class="line"><span style="--shiki-light:#24292E;--shiki-dark:#E1E4E8;">}</span></span></code></pre></div>`,11)]))}const y=s(n,[["render",e]]);export{d as __pageData,y as default};
